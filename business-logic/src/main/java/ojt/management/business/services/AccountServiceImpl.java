package ojt.management.business.services;


import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.CrudException;
import ojt.management.data.entities.Account;
import ojt.management.data.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {this.accountRepository = accountRepository;}

    @Override
    public Account getUserById(Long id) throws AccountIdNotExistedException {
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        } else
            return accountRepository.getById(id);
    }

    @Override
    public List<Account> searchUser(String name, String email, String phone) {
        if (name == null && email == null && phone == null) {
            return accountRepository.findAll();
        }
        return accountRepository.searchUser(name, email, phone);
    }

    @Override
    public Account updateUser(Long id, String phone, String address, String password) throws AccountIdNotExistedException{
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        } else {
            Account account = accountRepository.getById(id);
            if (account.isDisabled()) {
                throw new AccountIdNotExistedException();
            } else {
                if (phone != null) {
                    account.setPhone(phone);
                }
                if (address != null) {
                    account.getStudent().setAddress(address);
                }
                if (password != null) {
                    account.setPassword(password);
                }
                accountRepository.save(account);
                return accountRepository.getById(account.getId());
            }
        }
    }

    @Override
    public boolean deleteUser(Long id) throws AccountIdNotExistedException{
        if (Boolean.FALSE.equals(accountRepository.existsById(id))) {
            throw new AccountIdNotExistedException();
        } else {
            Account account = accountRepository.getById(id);
            if (!account.isDisabled()) {
                account.setDisabled(true);
            }
            return true;
        }
    }

}
