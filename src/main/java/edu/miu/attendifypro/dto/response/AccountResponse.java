package edu.miu.attendifypro.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private List<RoleResponse> roles;
}
