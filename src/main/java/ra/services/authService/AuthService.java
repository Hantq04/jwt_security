package ra.services.authService;

import ra.payloads.request.LoginRequest;
import ra.payloads.request.SignupRequest;
import ra.payloads.response.JwtResponse;
import ra.payloads.response.MessageResponse;

public interface AuthService {
    MessageResponse registerUser(SignupRequest signupRequest);

    JwtResponse loginUser(LoginRequest loginRequest);
}
