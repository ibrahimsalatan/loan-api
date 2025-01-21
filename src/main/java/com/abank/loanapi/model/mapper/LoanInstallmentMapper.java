package com.abank.loanapi.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abank.loanapi.model.dto.response.LoanInstallmentResponse;
import com.abank.loanapi.model.entity.LoanInstallment;

@Mapper(componentModel = "spring")
public interface LoanInstallmentMapper {

    @Mapping(source = "loan.id", target = "loanId")
    LoanInstallmentResponse toDTO(LoanInstallment installment);

    LoanInstallment toEntity(LoanInstallmentResponse installmentDTO);
}