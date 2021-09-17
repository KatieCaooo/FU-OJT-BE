package com.app.source.repositories;

import com.app.source.entities.Representative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepresentativeRepository extends JpaRepository<Representative, Long> {
}
