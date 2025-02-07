package ra.services.userService;

import ra.models.Users;

public interface UserService {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users insertUser(Users user);
}
