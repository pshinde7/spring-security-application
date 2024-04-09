package com.dreamcoder.client.repository;

import com.dreamcoder.client.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,String> {
    VerificationToken findByToken(String token);
}
