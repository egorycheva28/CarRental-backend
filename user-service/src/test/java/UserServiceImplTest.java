import com.example.userservice.dto.response.GetUser;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfileById_UserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setEmail("email@example.com");
        user.setPhone("89137894562");
        user.setLastName("Sasha1");
        user.setFirstName("Sasha2");
        user.setMiddleName("Sasha3");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        GetUser result = userService.getProfileById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals("email@example.com", result.email());
        assertEquals("89137894562", result.phone());
        assertEquals("Sasha1", result.lastName());
        assertEquals("Sasha2", result.firstName());
        assertEquals("Sasha3", result.middleName());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetProfileById_UserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getProfileById(userId);
        });

        assertEquals("Такого пользователя нет!", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }
}