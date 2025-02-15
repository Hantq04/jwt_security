package ra.services.authService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.enums.ERole;
import ra.jwt.JwtTokenProvider;
import ra.models.Roles;
import ra.models.Users;
import ra.payloads.request.LoginRequest;
import ra.payloads.request.SignupRequest;
import ra.payloads.response.JwtResponse;
import ra.payloads.response.MessageResponse;
import ra.security.CustomUserDetails;
import ra.services.roleService.RoleServiceImpl;
import ra.services.userService.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    UserServiceImpl userService;
    RoleServiceImpl roleService;
    PasswordEncoder passwordEncoder;

    @Override
    public MessageResponse registerUser(SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return new MessageResponse("Error: UserName is already used.");
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email is already used.");
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();
        String dateNow = simpleDateFormat.format(now);
        try {
            user.setCreated(simpleDateFormat.parse(dateNow));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setUserStatus(true);
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("ROLE_NOT_FOUND."));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN_NOT_FOUND."));
                        listRoles.add(adminRole);
                        break;
                    case "moderator":
                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("ROLE_MOD_NOT_FOUND."));
                        listRoles.add(modRole);
                        break;
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("ROLE_USER_NOT_FOUND."));
                        listRoles.add(userRole);
                        break;
                    default:
                        break;
                }
            });
        }
        user.setListRoles(listRoles);
        userService.insertUser(user);
        return new MessageResponse("User registered successfully.");
    }

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customUserDetails);
        List<String> listRoles = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return new JwtResponse(jwt, customUserDetails.getUsername(),
                customUserDetails.getEmail(), customUserDetails.getPhone(), listRoles);
    }
}
