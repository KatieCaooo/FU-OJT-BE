package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.payload.request.AccountUpdateRequest;
import ojt.management.data.entities.Account;
import ojt.management.data.repositories.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getUserById(Long id) throws AccountIdNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        } else {
            return accountRepository.getById(id);
        }
    }

    @Override
    public Page<Account> searchUser(Specification<Account> specification, Pageable pageable) {
        return accountRepository.findAll(specification, pageable);
    }

    @Override
    public Account updateUser(AccountUpdateRequest accountUpdateRequest) throws AccountIdNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(accountUpdateRequest.getId()))) {
            throw new AccountIdNotExistedException();
        } else {
            Account account = accountRepository.getById(accountUpdateRequest.getId());
            if (account.isDisabled()) {
                throw new AccountIdNotExistedException();
            }
            account.setPhone(accountUpdateRequest.getPhone());
            account.getStudent().setAddress(accountUpdateRequest.getAddress());
            account.setPassword(accountUpdateRequest.getPassword());
            return accountRepository.save(account);
        }
    }

    @Override
    public boolean deleteUser(Long id) throws AccountIdNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        } else {
            Account account = accountRepository.getById(id);
            if (!account.isDisabled()) {
                account.setDisabled(true);
                return true;
            } else {
                throw new AccountIdNotExistedException();
            }
        }
    }
}
