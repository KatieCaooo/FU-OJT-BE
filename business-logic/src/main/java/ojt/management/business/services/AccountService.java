package ojt.management.business.services;

import ojt.management.common.exceptions.AccountNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.request.AccountRequest;
import ojt.management.data.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AccountService {
    Account getUserById(Long id) throws AccountNotExistedException;

    Page<Account> searchUser(Specification<Account> specification, Pageable pageable);

    Account updateUser(Long id, AccountRequest accountUpdateRequest, Long accountId) throws  NotPermissionException, AccountNotExistedException;

    boolean deleteUser(Long id) throws AccountNotExistedException;

    String recoveryPassword(String email) throws AccountNotExistedException;
}
