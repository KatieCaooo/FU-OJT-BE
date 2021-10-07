package ojt.management.controllers;


import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.data.entities.Account;
import ojt.management.data.entities.Company;
import ojt.management.data.entities.Major;
import ojt.management.data.entities.Representative;
import ojt.management.data.entities.Student;
import ojt.management.exceptions.CompanyNotExistedException;
import ojt.management.common.exceptions.CrudException;
import ojt.management.exceptions.EmailAlreadyExistedException;
import ojt.management.exceptions.EmptyRoleException;
import ojt.management.exceptions.MajorNotExistedException;
import ojt.management.exceptions.UsernameAlreadyExistedException;
import ojt.management.payload.request.LoginRequest;
import ojt.management.payload.request.SignupRequest;
import ojt.management.common.payload.DataResponse;
import ojt.management.common.payload.JwtResponse;
import ojt.management.common.payload.Response;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.CompanyRepository;
import ojt.management.data.repositories.MajorRepository;
import ojt.management.data.repositories.RepresentativeRepository;
import ojt.management.data.repositories.StudentRepository;
import ojt.management.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;

    private final CompanyRepository companyRepository;

    private final MajorRepository majorRepository;

    private final StudentRepository studentRepository;

    private final RepresentativeRepository representativeRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;


    public AuthController(AuthenticationManager authenticationManager, AccountRepository accountRepository,
                          CompanyRepository companyRepository, MajorRepository majorRepository, StudentRepository studentRepository, RepresentativeRepository representativeRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.companyRepository = companyRepository;
        this.majorRepository = majorRepository;
        this.studentRepository = studentRepository;
        this.representativeRepository = representativeRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<DataResponse<Account>> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
            throws UsernameAlreadyExistedException, EmailAlreadyExistedException, EmptyRoleException, CompanyNotExistedException, MajorNotExistedException {
        if (signUpRequest.getStudentCode() != null && Boolean.TRUE.equals(accountRepository.existsByStudent_StudentCode(signUpRequest.getStudentCode()))) {
            throw new UsernameAlreadyExistedException();
        }

        if (Boolean.TRUE.equals(accountRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new EmailAlreadyExistedException();
        }

        // Create new user's account
        Account account = new Account(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getPhone());
        account = accountRepository.save(account);
        String strRole = signUpRequest.getRole();
        if (strRole == null) {
            throw new EmptyRoleException();
        } else {
            switch (strRole) {
                case "SYS_ADMIN":
                    account.setAdmin(true);
                    break;
                case "COMPANY_REPRESENTATIVE":
                    if (!companyRepository.existsById(signUpRequest.getCompanyId())) {
                        throw new CompanyNotExistedException();
                    }
                    Company company = companyRepository.getById(signUpRequest.getCompanyId());
                    Representative representative = new Representative();
                    representative.setCompany(company);
                    representative.setAccount(account);
                    representativeRepository.save(representative);
                    break;
                default:
                    if (!majorRepository.existsById(signUpRequest.getMajorId())) {
                        throw new MajorNotExistedException();
                    }
                    Major major = majorRepository.getById(signUpRequest.getMajorId());
                    Student student = new Student();
                    student.setStudentCode(signUpRequest.getStudentCode());
                    student.setMajor(major);
                    student.setAccount(account);
                    student.setAddress(signUpRequest.getAddress());
                    studentRepository.save(student);
                    break;
            }
        }
        account = accountRepository.save(account);
        return ResponseEntity.ok(new DataResponse<>("OK", "User registered successfully!", account));
    }

    @GetMapping("/error-example/{error}")
    public ResponseEntity<Response> errorMethod(@PathVariable boolean error) throws CrudException {
        if (error) {
            throw new CrudException("My custom error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(new Response("OK", "OK"));
    }
}
