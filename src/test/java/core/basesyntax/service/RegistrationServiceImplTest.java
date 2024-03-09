package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 27;
    private static final String DEFAULT_LOGIN = "curvabobr";
    private static final String DEFAULT_PASSWORD = "Defaultpassword17";
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 4;
    private static final int FIRST_LOGIN_CHARACTER = 0;

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void registration_user_ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(actual, user);
    }

    @Test
    void minimal_password_length_ok() {
        int actual = user.getPassword().length();
        assertTrue(actual > MINIMAL_PASSWORD_LENGTH,
                "minimal password length should be " + MINIMAL_PASSWORD_LENGTH);
    }

    @Test
    void minimal_age_ok() {
        int actual = user.getAge();
        assertTrue(actual > MINIMAL_AGE, "age should be greater than " + MINIMAL_AGE);
    }

    @Test
    void minimal_login_Length_ok() {
        int actual = user.getLogin().length();
        assertTrue(actual > MINIMAL_LOGIN_LENGTH, "login length should be longer than "
                + MINIMAL_AGE);
    }

    @Test
    void login_started_with_letter_ok() {
        char actual = user.getLogin().charAt(FIRST_LOGIN_CHARACTER);
        assertTrue(Character.isLetter(actual), "your login should start with any letter");
    }

    @Test
    void login_is_not_null_ok() {
        String actual = user.getLogin();
        assertNotNull(actual);
    }

    @Test
    void password_is_not_null_ok() {
        String actual = user.getPassword();
        assertNotNull(actual);
    }
}
