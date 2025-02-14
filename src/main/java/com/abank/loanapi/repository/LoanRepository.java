package com.abank.loanapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abank.loanapi.model.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
