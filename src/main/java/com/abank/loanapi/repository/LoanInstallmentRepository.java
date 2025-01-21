package com.abank.loanapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abank.loanapi.model.entity.LoanInstallment;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {

}
