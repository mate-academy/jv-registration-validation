package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.InvalidInputDataException;
import core.basesyntax.service.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword123";
    private static final Integer VALID_AGE = 33;
    private static final Integer NEGATIVE_AGE = -1;
    private static final Integer AGE_LESS_THAN_18 = 17;
    private static final String SHORT_LOGIN = "usr";
    private static final String SHORT_PASSWORD = "pwd";
    private static final String EXISTING_LOGIN = "existingUser";
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);

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
        User user = new User();
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(null);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin(SHORT_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(SHORT_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(NEGATIVE_AGE);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(AGE_LESS_THAN_18);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = new User();
        existingUser.setLogin(EXISTING_LOGIN);
        existingUser.setPassword(VALID_PASSWORD);
        existingUser.setAge(VALID_AGE);
        Storage.people.add(existingUser);

        User newUser = new User();
        newUser.setLogin(EXISTING_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(VALID_AGE);

        assertThrows(UserAlreadyExistsException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_validLogin_ok() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);

        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_validPassword_ok() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword("StrongPassword123");
        user.setAge(VALID_AGE);

        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getPassword(), registeredUser.getPassword());
    }
}
