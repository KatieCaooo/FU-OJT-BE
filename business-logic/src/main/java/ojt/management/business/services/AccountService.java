package ojt.management.business.services;

import ojt.management.data.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAllUsers();

    Optional<Account> getUserById(Long id);

    List<Account> searchUser(String name, String email, String phone);
}
