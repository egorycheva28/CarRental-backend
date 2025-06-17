import static com.example.security.services.UserDetailsImpl.authorities;
import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.dto.requests.EditUser;
import com.example.dto.response.GetUser;
import com.example.dto.response.SuccessResponse;
import com.example.exception.RegisterException;
import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.services.UserDetailsImpl;
import com.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;
import java.util.UUID;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setActive(false);

        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(userId, "Sasha1", "Sasha2", "Sasha3", "email@example.com", "password"));
    }

    @Test
    public void testGetProfile_ShouldReturnUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        GetUser result = userService.getProfile(authentication);

        assertNotNull(result);
        assertEquals(userId, result.id());
    }

    @Test
    public void testGetProfile_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getProfile(authentication));
    }

    @Test
    public void testActivateUser_ShouldActivateUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        SuccessResponse response = userService.activateUser(authentication);

        assertTrue(user.isActive());
        assertEquals("Статус пользователя успешно изменён!", response.message());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeactivateUser_ShouldDeactivateUser() {
        user.setActive(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        SuccessResponse response = userService.deactivateUser(authentication);

        assertFalse(user.isActive());
        assertEquals("Статус пользователя успешно изменён!", response.message());
        verify(userRepository, times(1)).save(user);
    }

    /*@Test
    public void testEditUser_ShouldEditUser() {
        user.setEmail("old@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        EditUser editData = new EditUser("NewLastName", "NewFirstName", null, null, "new@example.com", "123456789");

        SuccessResponse response = userService.editUser(editData, authentication);

        assertEquals("NewLastName", user.getLastName());
        assertEquals("NewFirstName", user.getFirstName());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("Пользователь успешно изменён!", response.message());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testEditUser_ShouldThrowRegisterException_WhenEmailExists() {
        user.setEmail("existing@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);
        EditUser editData = new EditUser(null, null, null, null, "new@example.com", null);

        assertThrows(RegisterException.class, () -> userService.editUser(editData, authentication));
    }

    @Test
    public void testEditUser_ShouldThrowRegisterException_WhenPhoneExists() {
        user.setPhone("123456789");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(userRepository.existsByPhone("987654321")).thenReturn(true);
        EditUser editData = new EditUser(null, null, null, null, null, "987654321");

        assertThrows(RegisterException.class, () -> userService.editUser(editData, authentication));
    }*/
}