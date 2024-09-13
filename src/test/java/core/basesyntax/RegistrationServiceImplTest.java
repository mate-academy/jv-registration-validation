package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private StorageDao storageDao;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        storageDao = mock(StorageDao.class);
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "User cannot be null");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login must be at least 6 characters long");
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("password123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login must be at least 6 characters long");
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");
        user.setAge(20);

        when(storageDao.get("existingUser")).thenReturn(new User());

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with login existingUser already exists");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password must be at least 6 characters long");
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abc");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password must be at least 6 characters long");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User must be at least 18 years old");
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User must be at least 18 years old");
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password123");
        user.setAge(20);

        when(storageDao.add(user)).thenReturn(user);

        User result = registrationService.register(user);
        assertEquals(user, result);
        verify(storageDao, times(1)).add(user);
    }
}
