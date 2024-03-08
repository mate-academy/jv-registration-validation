package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeAll
    public static void setUpAll() {
        final StorageDao storage = new StorageDaoImpl();
        User existingUser = new User();
        existingUser.setAge(20);
        existingUser.setLogin("existingUser");
        existingUser.setPassword("password123");
        storage.add(existingUser);
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_UserOk_notNull() throws ValidationException {
        User user = new User();
        user.setAge(20);
        user.setLogin("username");
        user.setPassword("password123");
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_LoginShort_Exception() {
        User user = new User();
        user.setAge(20);
        user.setLogin("user");
        user.setPassword("password123");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Login shorter than 6 symbols.user", exception.getMessage());
    }

    @Test
    void register_PasswordShort_Exception() {
        User user = new User();
        user.setAge(20);
        user.setLogin("username");
        user.setPassword("pass");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Password shorter than 6 symbols.pass", exception.getMessage());
    }

    @Test
    void register_AgeIncorrect_Exception() {
        User user = new User();
        user.setAge(17);
        user.setLogin("username");
        user.setPassword("password123");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("Age is incorrect17", exception.getMessage());
    }

    @Test
    void testRegister_UserAlreadyExists_Exception() {
        User user = new User();
        user.setAge(20);
        user.setLogin("existingUser");
        user.setPassword("password123");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals("We already have such user.existingUser", exception.getMessage());
    }
}
