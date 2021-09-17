package com.app.source.repositories;

import com.app.source.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

    Boolean existsByStudent_StudentCode(String studentCode);

    Boolean existsByEmail(String email);
}
