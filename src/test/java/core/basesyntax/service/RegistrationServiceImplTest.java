package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final String VALID_LOGIN = "validUser";
    private static final String VALID_PASSWORD = "validPass";
    private static final int VALID_AGE = 25;

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl(new StorageDaoImpl());
    }

    @Test
    void register_validUser_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createUser("short", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createUser(VALID_LOGIN, "12345", VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_edgeAge_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, 18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        User existingUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(existingUser);

        User newUser = createUser(VALID_LOGIN, "anotherPass", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
