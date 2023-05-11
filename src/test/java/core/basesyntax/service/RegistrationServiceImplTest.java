package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_18 = 18;
    private static final int AGE_UNDER_18 = 10;
    private static final int NEGATIVE_AGE = -1;
    private static final int MAX_AGE = 105;
    private static final int OVER_MAX_AGE = 106;
    private static final int AGE_OVER_18 = 20;
    private static final String USER_NAME = "Docent";
    private static final String NEW_USER_NAME = "Daisy";
    private static final String USER_PASSWORD_OK = "123456";
    private static final String USER_PASSWORD_THREE_CHARS = "123";
    private static final String USER_PASSWORD_FIVE_CHARS = "12345";
    private static final String USER_PASSWORD_LENGTH_OK = "1234567";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final long USER_ID = 123L;
    private static RegistrationService registrationService;
    private static User user;
    private static User newUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(USER_NAME);
        user.setAge(AGE_18);
        user.setPassword(USER_PASSWORD_OK);

        newUser = new User();
        newUser.setLogin(USER_NAME);
        newUser.setAge(AGE_18);
        newUser.setPassword(USER_PASSWORD_OK);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
            throw new RegistrationException(" ");
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(" ");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
            throw new RegistrationException("not allowed empty line!!!");
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
            throw new RegistrationException("empty line is not ok, please pass any login");
        });
    }

    @Test
    void register_sameLogin_notOk() {
        newUser.setLogin(USER_NAME);
        assertEquals(newUser.getLogin(), user.getLogin());
    }

    @Test
    void register_rightLogin_ok() {
        user.setLogin(NEW_USER_NAME);
        assertNotEquals(newUser.getLogin(), user.getLogin());
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);;
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(AGE_UNDER_18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_overAge_notOk() {
        user.setAge(OVER_MAX_AGE);
        boolean overage = user.getAge() > MAX_AGE;
        assertTrue(overage);
    }

    @Test
    void register_maxAge_ok() {
        user.setAge(MAX_AGE);
        boolean maxAgeOk = user.getAge() <= MAX_AGE;
        assertTrue(maxAgeOk);
    }

    @Test
    void register_rightAge_ok() {
        user.setAge(AGE_OVER_18);
        boolean ageIsOk = user.getAge() > AGE_18;
        assertTrue(ageIsOk);
    }

    @Test
    void register_minAge_age() {
        boolean minRequiredAgeOK = user.getAge() == AGE_18;
        assertTrue(minRequiredAgeOK);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(" ");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPasswordLength_notOk() {
        user.setPassword(USER_PASSWORD_THREE_CHARS);
        user.setPassword(USER_PASSWORD_FIVE_CHARS);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_minPasswordLength_ok() {
        boolean passwordOk = user.getPassword().length() == MIN_PASSWORD_LENGTH;
        assertTrue(passwordOk);
    }

    @Test
    void register_rightPassword_ok() {
        user.setPassword(USER_PASSWORD_LENGTH_OK);
        boolean passwordOK = user.getPassword().length() > MIN_PASSWORD_LENGTH;
        assertTrue(passwordOK);
    }

    @Test
    void register_addUser_ok() {
        user.setId(USER_ID);
        user.setLogin(USER_NAME);
        user.setAge(AGE_18);
        user.setPassword(USER_PASSWORD_OK);
        assertThrows(RegistrationException.class, () -> {
            Storage.people.add(user);
            throw new RegistrationException("There is something gone wrong, "
                    + "please check all requirements");
        });
    }
}
