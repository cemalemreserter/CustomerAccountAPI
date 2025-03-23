package org.customer.mapping

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
internal interface AccountMapper {
    companion object {
        val INSTANCE: AccountMapper? = Mappers.getMapper(AccountMapper::class.java)
    }
}
