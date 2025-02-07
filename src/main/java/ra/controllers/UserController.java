package ra.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.payloads.request.LoginRequest;
import ra.payloads.request.SignupRequest;
import ra.payloads.response.JwtResponse;
import ra.payloads.response.MessageResponse;
import ra.services.authService.AuthServiceImpl;

import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/auth")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        MessageResponse response = authService.registerUser(signupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("--- Login ---");
        JwtResponse response = authService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }
}
