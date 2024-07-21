package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.InvalidInputDataException;
import core.basesyntax.service.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword123";
    private static final Integer VALID_AGE = 33;
    private static final Integer NEGATIVE_AGE = -1;
    private static final Integer AGE_LESS_THAN_18 = 17;
    private static final String SHORT_LOGIN = "short";
    private static final String SHORT_PASSWORD = "pass5";
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void register_validUser_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "Registered user should not be null");
        assertEquals(VALID_LOGIN, registeredUser.getLogin());
        assertEquals(VALID_PASSWORD, registeredUser.getPassword());
        assertEquals(VALID_AGE, registeredUser.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, NEGATIVE_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, AGE_LESS_THAN_18);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(existingUser);
        int numberOfUsersBefore = Storage.people.size();

        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

        assertThrows(UserAlreadyExistsException.class, () -> registrationService.register(newUser));
        int numberOfUsersAfter = Storage.people.size();
        assertEquals(numberOfUsersBefore, numberOfUsersAfter,
                "User count should not increase when attempting to register an existing user");
    }

    @Test
    void register_validLogin_ok() {
        User user = createUser("newUser", VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_validPassword_ok() {
        User user =  createUser(VALID_LOGIN, "StrongPassword123", VALID_AGE);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getPassword(), registeredUser.getPassword());
    }
}
