package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "kandibober";
    private static final String VALID_PASSWORD = "ibrahim13";
    private static final String INVALID_LOGIN = "kandi";
    private static final String INVALID_PASSWORD = "ibr13";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User expected = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(expected);
        assertNotNull(actual);
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    void register_userIsNull_exceptionThrown() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    public void register_userWithExistingLogin_exceptionThrown() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        Storage.people.add(user);
        User userWithSameLogin = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSameLogin));
    }

    @Test
    public void register_nullLogin_exceptionThrown() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_exceptionThrown() {
        User user = createUser(INVALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_exceptionThrown() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_exceptionThrown() {
        User user = createUser(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_exceptionThrown() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_exceptionThrown() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
