package com.backend.internalhackthon.Repository;

import com.backend.internalhackthon.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User , Long> {

     Optional<User> findByEmail(String email);

}
