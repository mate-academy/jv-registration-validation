package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN_VALUE = "ABC1234";
    private static final String VALID_PASSWORD_VALUE = "abc1234";
    private static final Integer VALID_AGE_VALUE = 18;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_success() {
        User user = createUser(VALID_LOGIN_VALUE, VALID_PASSWORD_VALUE, VALID_AGE_VALUE);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_nullUser_throwsException() {
        User user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLoginValue_throwsException() {
        User user = createUser(null, VALID_PASSWORD_VALUE, VALID_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLoginValue_throwsException() {
        User user = createUser("abc", VALID_PASSWORD_VALUE, VALID_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_edgeLoginValue_throwsException() {
        User user = createUser("abc12", VALID_PASSWORD_VALUE, VALID_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPasswordValue_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, null, VALID_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPasswordValue_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, "abc", VALID_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_edgePasswordValue_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, "abc12", VALID_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAgeValue_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, VALID_PASSWORD_VALUE, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAgeValue_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, VALID_PASSWORD_VALUE, 10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_edgeAgeValue_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, VALID_PASSWORD_VALUE, 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_throwsException() {
        User user = createUser(VALID_LOGIN_VALUE, VALID_PASSWORD_VALUE, VALID_AGE_VALUE);
        Storage.people.add(user);

        User duplicateUser = createUser(VALID_LOGIN_VALUE, VALID_PASSWORD_VALUE, VALID_AGE_VALUE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(duplicateUser));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

}
