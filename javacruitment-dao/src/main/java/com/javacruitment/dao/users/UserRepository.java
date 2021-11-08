package com.javacruitment.dao.users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.javacruitment.dao.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByUsernameContaining(String username);
}
