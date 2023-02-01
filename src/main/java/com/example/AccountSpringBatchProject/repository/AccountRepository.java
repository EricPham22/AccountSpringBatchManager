package com.example.AccountSpringBatchProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AccountSpringBatchProject.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

}
