package ojt.management.business.services;


import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.NotPermissionException;
import ojt.management.common.payload.request.AccountRequest;
import ojt.management.data.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AccountService {
    Account getUserById(Long id) throws AccountIdNotExistedException;

    Page<Account> searchUser(Specification<Account> specification, Pageable pageable);

    Account updateUser(Long id, AccountRequest accountUpdateRequest, Long accountId) throws AccountIdNotExistedException, NotPermissionException;
    Account updatePassword(Long id, AccountRequest accountUpdateRequest, Long accountId) throws AccountIdNotExistedException, NotPermissionException;

    boolean deleteUser(Long id) throws AccountIdNotExistedException;
}
