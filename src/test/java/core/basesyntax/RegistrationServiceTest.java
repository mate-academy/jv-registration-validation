package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private StorageDao storageDao;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        user = new User();
    }

    @Test
    void registerNullUser_NotOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(null));

        assertEquals("User is null.", exception.getMessage());
    }

    @Test
    void registerLoginNull_NotOk() {
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Login can't be null.", exception.getMessage());
    }

    @Test
    void registerPasswordNull_NotOk() {
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Password can't be null.", exception.getMessage());
    }

    @Test
    void registerUserAgeNull_NotOk() {
        user.setLogin("validLog");
        user.setPassword("validPwd");
        user.setAge(null);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Age can't be null.", exception.getMessage());
    }

    @Test
    void registerPasswordExists_NotOk() throws RegistrationException {
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("password123");
        user.setAge(21);
        storageDao.add(user);

        User newUser = new User();
        newUser.setLogin("existingUser");
        newUser.setPassword("password123");
        newUser.setAge(21);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));

        assertEquals("Login already exists.", exception.getMessage());
    }

    @Test
    void registerLogin_NotOk() throws RegistrationException {
        String[] invalidLogins = {"1", "ab", "son", "hell", "user"};

        for (String login : invalidLogins) {
            user.setLogin(login);
            user.setPassword("validPassword");
            user.setAge(20);

            RegistrationException exception = assertThrows(RegistrationException.class, () ->
                    registrationService.register(user));

            assertEquals("Login length must be at least 6 characters.", exception.getMessage());
        }

        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void registerLoginExists_NotOk() throws RegistrationException {
        User existingUser = new User();
        existingUser.setLogin("existingUser");
        existingUser.setPassword("password123");
        existingUser.setAge(20);
        storageDao.add(existingUser);

        User newUserWithExistingLogin = new User();
        newUserWithExistingLogin.setLogin("existingUser");
        newUserWithExistingLogin.setPassword("newPassword123");
        newUserWithExistingLogin.setAge(25);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUserWithExistingLogin);
        });

        assertEquals("Login already exists.", exception.getMessage());
    }


    @Test
    void registerPassword_NotOk() throws RegistrationException {
        String[] invalidPasswords = {"1", "ab", "son", "user", "hell0"};

        for (String password : invalidPasswords) {
            user.setLogin("validLog");
            user.setPassword(password);
            user.setAge(20);

            RegistrationException exception = assertThrows(RegistrationException.class, () ->
                    registrationService.register(user));

            assertEquals("Password length must be at least 6 characters.", exception.getMessage());
        }
    }

    @Test
    void registerUserAge_NotOk() {
        for (int i = 0; i < 18; i++) {
            user.setLogin("validLogin");
            user.setPassword("validPassword");
            user.setAge(i);

            RegistrationException exception = assertThrows(RegistrationException.class, () ->
                    registrationService.register(user));

            assertEquals("Age must be at least 18 years old.", exception.getMessage());
        }

        user.setLogin("validLogin18");
        user.setPassword("validPassword");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_Ok() {
        user.setLogin("validUser");
        user.setPassword("validPassword123");
        user.setAge(20);

        User registeredUser = assertDoesNotThrow(() -> registrationService.register(user));

        assertNotNull(registeredUser);
        assertEquals("validUser", registeredUser.getLogin());
        assertEquals("validPassword123", registeredUser.getPassword());
        assertEquals(20, registeredUser.getAge());

        User storedUser = storageDao.get("validUser");
        assertNotNull(storedUser);
        assertEquals("validUser", storedUser.getLogin());
        assertEquals("validPassword123", storedUser.getPassword());
        assertEquals(20, storedUser.getAge());
    }

}
