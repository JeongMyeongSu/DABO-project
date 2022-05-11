package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.repository.entity.DABOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DABOUserRepository extends JpaRepository<DABOUser, Long> {
    Optional<DABOUser> findDABOUserByUserId(Long id);
    Optional<DABOUser> findDABOUserByEmail(String email);
    Optional<DABOUser> findDABOUserByNickname(String nickname);
}
