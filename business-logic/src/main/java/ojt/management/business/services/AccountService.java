package ojt.management.business.services;

import ojt.management.common.exceptions.AccountIdNotExistException;
import ojt.management.data.entities.Account;

import java.util.List;

public interface AccountService {
    Account getUserById(Long id) throws AccountIdNotExistException;

    List<Account> searchUser(String name, String email, String phone);

    Account updateUser(Long id, String phone, String address, String password) throws AccountIdNotExistException;

    boolean deleteUser(Long id) throws AccountIdNotExistException;
}