package ra.payloads.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    String userName;
    String password;
    String email;
    String phone;
    Date created;
    boolean userStatus;
    Set<String> listRoles;
}
