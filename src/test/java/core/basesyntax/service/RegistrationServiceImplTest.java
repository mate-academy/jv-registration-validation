package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.MyRegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
    private static final long NEW_USER_ID = 1234L;
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private static User user;
    private static User newUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_NAME);
        user.setAge(AGE_18);
        user.setPassword(USER_PASSWORD_OK);

        newUser = new User();
        newUser.setId(USER_ID);
        newUser.setLogin(USER_NAME);
        newUser.setAge(AGE_18);
        newUser.setPassword(USER_PASSWORD_OK);
    }

    @Test
    void user_null_notOk() {
        user = null;
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
            throw new MyRegistrationException(" ");
        });
    }

    @Test
    void empty_Line_for_login_notOk() {
        user.setLogin(" ");
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
            throw new MyRegistrationException("not allowed empty line!!!");
        });
    }

    @Test
    void null_value_for_user_login_is_notOk() {
        user.setLogin(null);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
            throw new MyRegistrationException("empty line is not ok, please pass any login");
        });
    }

    @Test
    void there_is_such_a_login_in_the_storage_notOk() {
        newUser.setLogin(USER_NAME);
        assertEquals(newUser.getLogin(), user.getLogin());
    }

    @Test
    void appropriate_login_ok() {
        user.setLogin(NEW_USER_NAME);
        assertNotEquals(newUser.getLogin(), user.getLogin());
    }

    @Test
    void null_value_for_age_notOk() {
        user.setAge(null);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void negative_age_is_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_less_than_eighteens_is_notOk() {
        user.setAge(AGE_UNDER_18);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void age_maxValue_notOk() {
        user.setAge(OVER_MAX_AGE);
        boolean overage = user.getAge() > MAX_AGE;
        assertTrue(overage);
    }

    @Test
    void age_maxValue_ok() {
        user.setAge(MAX_AGE);
        boolean maxAgeOk = user.getAge() <= MAX_AGE;
        assertTrue(maxAgeOk);
    }

    @Test
    void appropriate_age_ok() {
        user.setAge(AGE_OVER_18);
        boolean ageIsOk = user.getAge() > AGE_18;
        assertTrue(ageIsOk);
    }

    @Test
    void min_required_age() {
        boolean minRequiredAgeOK = user.getAge() == AGE_18;
        assertTrue(minRequiredAgeOK);
    }

    @Test
    void null_password_notOk() {
        user.setPassword(null);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void empty_line_for_password_notOk() {
        user.setPassword(" ");
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void password_less_than_six_characters_notOk() {
        user.setPassword(USER_PASSWORD_THREE_CHARS);
        user.setPassword(USER_PASSWORD_FIVE_CHARS);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void appropriate_password_ok() {
        boolean passwordOk = user.getPassword().length() == MIN_PASSWORD_LENGTH;
        assertTrue(passwordOk);
    }

    @Test
    void password_more_then_six_chars_ok() {
        user.setPassword(USER_PASSWORD_LENGTH_OK);
        boolean passwordOK = user.getPassword().length() > MIN_PASSWORD_LENGTH;
        assertTrue(passwordOK);
    }

    @Test
    void add_user_ok() {
        user.setId(USER_ID);
        user.setLogin(USER_NAME);
        user.setAge(AGE_18);
        user.setPassword(USER_PASSWORD_OK);
        assertThrows(MyRegistrationException.class, () -> {
            Storage.people.add(user);
            throw new MyRegistrationException("There is something gone wrong, "
                    + "please check all requirements");
        });
    }
}
