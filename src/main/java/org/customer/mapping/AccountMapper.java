package org.customer.mapping;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
interface AccountMapper {
  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
}
