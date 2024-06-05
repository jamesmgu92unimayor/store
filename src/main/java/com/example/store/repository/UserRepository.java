package com.example.store.repository;

import com.example.store.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "select count(u)>0 from UserEntity u where u.email=:email and u.id<>:userId")
    boolean existsByEmailAndUser(String email, UUID userId);

}
