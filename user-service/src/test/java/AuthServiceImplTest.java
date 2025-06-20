import com.example.userservice.dto.requests.LoginUser;
import com.example.userservice.dto.requests.RefreshTokenRequest;
import com.example.userservice.dto.requests.RegisterUser;
import com.example.userservice.dto.response.GetToken;
import com.example.userservice.exception.LoginException;
import com.example.userservice.exception.RegisterException;
import com.example.userservice.model.RefreshToken;
import com.example.userservice.model.Role;
import com.example.userservice.model.Roles;
import com.example.userservice.model.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.jwt.JwtUtils;
import com.example.userservice.service.impl.AuthServiceImpl;
import com.example.userservice.service.impl.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUser_EmailExists() {
        //Arrange
        RegisterUser registerUser = new RegisterUser("sasha1", "sasha2", "sasha3", 5L, "89467588475", "test@example.com", "password", Set.of(new Role()));
        when(userRepository.existsByEmail(registerUser.email())).thenReturn(true);

        //Act
        RegisterException exception = assertThrows(RegisterException.class, () -> {
            authService.registerUser(registerUser);
        });

        //Assert
        assertEquals("Пользователь с такой почтой уже есть!", exception.getMessage());
    }

    @Test
    public void registerUser_PhoneExists() {
        //Arrange
        RegisterUser registerUser = new RegisterUser("sasha1", "sasha2", "sasha3", 5L, "89467588475", "test@example.com", "password", Set.of(new Role()));
        when(userRepository.existsByPhone(registerUser.phone())).thenReturn(true);

        //Act
        RegisterException exception = assertThrows(RegisterException.class, () -> {
            authService.registerUser(registerUser);
        });

        //Assert
        assertEquals("Пользователь с таким номером телефона уже есть!", exception.getMessage());
    }

    @Test
    public void loginUser_PasswordWrong() {
        //Arrange
        LoginUser loginUser = new LoginUser("test@example.com", "password");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException(""));

        //Act
        LoginException exception = assertThrows(LoginException.class, () -> {
            authService.loginUser(loginUser);
        });

        //Assert
        assertEquals("Неправильный пароль!", exception.getMessage());
    }

    @Test
    public void refreshToken_Success() {
        //Arrange
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("mockRefreshToken");
        when(refreshTokenService.refreshToken(any())).thenReturn(new GetToken("mockAccessToken", "mockRefreshToken"));

        //Act
        GetToken token = authService.refreshToken(refreshTokenRequest);

        //Assert
        assertEquals("mockAccessToken", token.accessToken());
        assertEquals("mockRefreshToken", token.refreshToken());
    }

}
