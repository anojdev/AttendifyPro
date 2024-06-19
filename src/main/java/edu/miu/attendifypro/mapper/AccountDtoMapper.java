package edu.miu.attendifypro.mapper;

import edu.miu.attendifypro.domain.auth.Account;
import edu.miu.attendifypro.domain.auth.Role;
import edu.miu.attendifypro.dto.response.AccountResponse;
import edu.miu.attendifypro.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountDtoMapper {

    AccountDtoMapper dtoMapper =
            Mappers.getMapper(AccountDtoMapper.class);
    AccountResponse accountToAccountResponse(Account account);

    RoleResponse roleToRoleResponse(Role role);
}
