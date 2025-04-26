package com.backend.internalhackthon.Repository;

import com.backend.internalhackthon.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User , Long> {

    public User findByEmail(String email);

}
