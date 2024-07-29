package com.example.oauth.repository;

import com.example.oauth.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String> {
    Certification findByUserId(String userId);

    @Transactional
    void deleteByUserId(String userId);
}
