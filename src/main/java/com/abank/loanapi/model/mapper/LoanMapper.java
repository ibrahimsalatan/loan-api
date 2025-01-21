package com.abank.loanapi.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.abank.loanapi.model.dto.response.LoanResponse;
import com.abank.loanapi.model.entity.Loan;

@Mapper(componentModel = "spring", uses = { LoanInstallmentMapper.class })
public interface LoanMapper {

    @Mapping(source = "customer.id", target = "customerId")
    LoanResponse toDTO(Loan loan);

    Loan toEntity(LoanResponse loanDTO);
}