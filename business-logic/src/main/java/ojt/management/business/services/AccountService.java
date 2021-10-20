package ojt.management.business.services;


import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.payload.request.AccountUpdateRequest;
import ojt.management.data.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AccountService {
    Account getUserById(Long id) throws AccountIdNotExistedException;

    Page<Account> searchUser(Specification<Account> specification, Pageable pageable);

    Account updateUser(Long id, AccountUpdateRequest accountUpdateRequest) throws AccountIdNotExistedException;

    boolean deleteUser(Long id) throws AccountIdNotExistedException;
}
