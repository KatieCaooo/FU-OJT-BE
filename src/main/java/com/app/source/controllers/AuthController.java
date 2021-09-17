package com.app.source.controllers;

import com.app.source.configuration.security.services.UserDetailsImpl;
import com.app.source.entities.Account;
import com.app.source.entities.Company;
import com.app.source.entities.Major;
import com.app.source.entities.Representative;
import com.app.source.entities.Student;
import com.app.source.exceptions.CompanyNotExistedException;
import com.app.source.exceptions.CrudException;
import com.app.source.exceptions.EmailAlreadyExistedException;
import com.app.source.exceptions.EmptyRoleException;
import com.app.source.exceptions.MajorNotExistedException;
import com.app.source.exceptions.UsernameAlreadyExistedException;
import com.app.source.payload.request.LoginRequest;
import com.app.source.payload.request.SignupRequest;
import com.app.source.payload.response.DataResponse;
import com.app.source.payload.response.JwtResponse;
import com.app.source.payload.response.Response;
import com.app.source.repositories.AccountRepository;
import com.app.source.repositories.CompanyRepository;
import com.app.source.repositories.MajorRepository;
import com.app.source.repositories.RepresentativeRepository;
import com.app.source.repositories.StudentRepository;
import com.app.source.utils.JwtUtils;
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
