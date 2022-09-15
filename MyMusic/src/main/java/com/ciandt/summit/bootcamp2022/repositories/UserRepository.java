package com.ciandt.summit.bootcamp2022.repositories;

import com.ciandt.summit.bootcamp2022.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}