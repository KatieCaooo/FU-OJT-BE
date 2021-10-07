package ojt.management.data.repositories;

import ojt.management.data.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    @Query("SELECT a FROM Account a where a.name = :name or a.email = :email or a.phone = :phone ")
    List<Account> searchUser(@Param("name") String name, @Param("email") String email, @Param("phone") String phone);

    Boolean existsByStudent_StudentCode(String studentCode);

    Boolean existsByEmail(String email);

    boolean existsById(Long id);

}
