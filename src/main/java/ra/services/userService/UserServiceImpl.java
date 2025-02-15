package ra.services.userService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ra.models.Users;
import ra.repositories.UserRepo;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepo userRepo;

    @Override
    public Users findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepo.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Users insertUser(Users user) {
        return userRepo.save(user);
    }
}
