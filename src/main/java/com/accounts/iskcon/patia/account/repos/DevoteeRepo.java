package com.accounts.iskcon.patia.account.repos;

import com.accounts.iskcon.patia.account.entities.Devotee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevoteeRepo extends JpaRepository<Devotee, Long> {

    Optional<Devotee> findByMobileNumber(Long mobileNumber);

    boolean existsByMobileNumber(Long mobileNumber);
}
