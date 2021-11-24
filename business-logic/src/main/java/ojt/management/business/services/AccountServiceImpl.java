package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.request.AccountRequest;
import ojt.management.data.entities.Account;
import ojt.management.data.repositories.AccountRepository;
import ojt.management.data.repositories.MajorRepository;
import ojt.management.data.repositories.SemesterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MajorRepository majorRepository;
    private final SemesterRepository semesterRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              MajorRepository majorRepository,
                              SemesterRepository semesterRepository) {
        this.accountRepository = accountRepository;
        this.majorRepository = majorRepository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    public Account getUserById(Long id) throws AccountIdNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        }
        Account account = accountRepository.getById(id);
        if (account.isDisabled() == true) {
            throw new AccountIdNotExistedException();
        }
        return account;
    }

    @Override
    public Page<Account> searchUser(Specification<Account> specification, Pageable pageable) {
        return accountRepository.findAll(specification, pageable);
    }

    @Override
    public Account updateUser(Long id, AccountRequest accountUpdateRequest, Long accountId)
            throws AccountIdNotExistedException, NotPermissionException {
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        }
        Account accountUpdated = accountRepository.getById(id);
        Account accountCurrent = accountRepository.getById(accountId);
        // Update student
        if (accountUpdated.getStudent() != null && !accountUpdated.isDisabled()) { //account updated is student
            if ((accountCurrent.isAdmin())) {
                //admin update profile for student
                accountUpdated.setEmail(accountUpdateRequest.getEmail());
                accountUpdated.setAvatar(accountUpdateRequest.getAvatar());
                accountUpdated.setName(accountUpdateRequest.getName());
                accountUpdated.setPhone(accountUpdateRequest.getPhone());
                accountUpdated.getStudent().setAddress(accountUpdateRequest.getAddress());
                accountUpdated.getStudent().setStudentCode(accountUpdateRequest.getStudentCode());
                accountUpdated.getStudent().setMajor(majorRepository.getById(accountUpdateRequest.getMajorId()));
                accountUpdated.getStudent().setSemester(semesterRepository.getById(accountUpdateRequest.getSemesterId()));
                accountRepository.save(accountUpdated);
            } else if ((accountCurrent.getStudent() != null && accountUpdated.equals(accountCurrent))) {
                //student update own profile
                accountUpdated.setPassword(accountUpdateRequest.getPassword());
                accountUpdated.setAvatar(accountUpdateRequest.getAvatar());
                accountUpdated.setPhone(accountUpdateRequest.getPhone());
                accountUpdated.getStudent().setAddress(accountUpdateRequest.getAddress());
                accountRepository.save(accountUpdated);
            } else {
                throw new NotPermissionException();
            }
            // Update Rep
        } else if (accountUpdated.getRepresentative() != null && !accountUpdated.isDisabled()) {
            if ((accountCurrent.isAdmin())) {
                //admin update profile for rep
                accountUpdated.setEmail(accountUpdateRequest.getEmail());
                accountUpdated.setAvatar(accountUpdateRequest.getAvatar());
                accountUpdated.setName(accountUpdateRequest.getName());
                accountUpdated.setPhone(accountUpdateRequest.getPhone());
                accountUpdated.getRepresentative().getCompany().setName(accountUpdateRequest.getCompanyName());
                accountUpdated.getRepresentative().getCompany().setAddress(accountUpdateRequest.getCompanyAddress());
                accountUpdated.getRepresentative().getCompany().setDescription(accountUpdateRequest.getDescription());
                accountRepository.save(accountUpdated);
            } else if ((accountCurrent.getRepresentative() != null && accountUpdated.equals(accountCurrent))) {
                //rep update own profile
                accountUpdated.setPassword(accountUpdateRequest.getPassword());
                accountUpdated.setAvatar(accountUpdateRequest.getAvatar());
                accountUpdated.setPhone(accountUpdateRequest.getPhone());
                accountUpdated.getRepresentative().getCompany().setName(accountUpdateRequest.getCompanyName());
                accountRepository.save(accountUpdated);
            } else {
                throw new NotPermissionException();
            }
            // Update Admin
        } else {
            if (accountCurrent.isAdmin()) {
                accountUpdated.setEmail(accountUpdateRequest.getEmail());
                accountUpdated.setPassword(accountUpdateRequest.getPassword());
                accountUpdated.setAvatar(accountUpdateRequest.getAvatar());
                accountUpdated.setName(accountUpdateRequest.getName());
                accountUpdated.setPhone(accountUpdateRequest.getPhone());
                accountRepository.save(accountUpdated);
            } else {
                throw new NotPermissionException();
            }
        }
        return accountUpdated;
    }

    @Override
    public boolean deleteUser(Long id) throws AccountIdNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        } else {
            Account account = accountRepository.getById(id);
            if (account.isDisabled() == false) {
                account.setDisabled(true);
                accountRepository.save(account);
                return true;
            } else {
                throw new AccountIdNotExistedException();
            }
        }
    }
}
