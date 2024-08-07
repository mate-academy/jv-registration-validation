package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String SHORT_LOGIN = "short";
    private static final String VALID_LOGIN = "validLogin";
    private static final String LONGER_LOGIN = "longerLogin";
    private static final String SHORT_PASSWORD = "short";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String LONGER_PASSWORD = "longerPassword";
    private static final Integer VALID_AGE = 20;
    private static final Integer UNDERAGE = 17;
    private static final Integer EXACTLY_AT_AGE = 18;
    private static final String EXISTING_LOGIN = "existingLogin";

    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_exactlyAtLoginLength_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(VALID_LOGIN, registeredUser.getLogin());
        assertEquals(VALID_PASSWORD, registeredUser.getPassword());
        assertEquals(VALID_AGE, registeredUser.getAge());
    }

    @Test
    public void register_longerLogin_ok() {
        User user = createUser(LONGER_LOGIN, VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(LONGER_LOGIN, registeredUser.getLogin());
        assertEquals(VALID_PASSWORD, registeredUser.getPassword());
        assertEquals(VALID_AGE, registeredUser.getAge());
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_exactlyAtPasswordLength_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(VALID_LOGIN, registeredUser.getLogin());
        assertEquals(VALID_PASSWORD, registeredUser.getPassword());
        assertEquals(VALID_AGE, registeredUser.getAge());
    }

    @Test
    public void register_longerPassword_ok() {
        User user = createUser(VALID_LOGIN, LONGER_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(VALID_LOGIN, registeredUser.getLogin());
        assertEquals(LONGER_PASSWORD, registeredUser.getPassword());
        assertEquals(VALID_AGE, registeredUser.getAge());
    }

    @Test
    public void register_nullAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_atAgeBoundary_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, EXACTLY_AT_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(VALID_LOGIN, registeredUser.getLogin());
        assertEquals(VALID_PASSWORD, registeredUser.getPassword());
        assertEquals(EXACTLY_AT_AGE, registeredUser.getAge());
    }

    @Test
    public void register_existingLogin_notOk() {
        User existingUser = createUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(existingUser);

        User newUser = createUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
