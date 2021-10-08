package com.yhkim.springsecuritypractice.mapping;

import com.yhkim.springsecuritypractice.controller.dto.AccountDTO;
import com.yhkim.springsecuritypractice.repository.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "kakaoId", source = "id")
    Account accountDtoToEntity(AccountDTO.LoginObject loginObject);
}
