package com.myweb.madules.users.repository;

import com.myweb.madules.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users,Long> {

    @Query("select u from Users u where u.email=:email")
    Users findByQuery(@Param("email")String email);

    Users findByEmail(String email);
}
