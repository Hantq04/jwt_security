package ra.payloads.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {
    String token;
    String type = "Bearer ";
    String userName;
    String email;
    String phone;
    List<String> listRoles;

    public JwtResponse(String token, String userName, String email, String phone, List<String> listRoles) {
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.listRoles = listRoles;
    }
}
