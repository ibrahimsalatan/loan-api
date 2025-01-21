package com.abank.loanapi.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanInstallment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "loan_id", nullable = false)
        private Loan loan;

        private BigDecimal amount;
        @Builder.Default()
        private BigDecimal paidAmount = BigDecimal.ZERO;
        private LocalDate dueDate;
        private LocalDate paymentDate;

        @Builder.Default()
        private Boolean isPaid = false;
}