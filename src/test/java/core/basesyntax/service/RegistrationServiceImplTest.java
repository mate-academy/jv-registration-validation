package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWPRD_LENGTH = 6;

    private static RegistrationServiceImpl registrationService;

    @BeforeEach
    void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        registrationService = new RegistrationServiceImpl();
        User user = null;
        assertThrows(NullPointerException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_minAge_ok() {
        User userOne = new User();
        userOne.setAge(MIN_AGE);
        userOne.setLogin("UserOne");
        userOne.setPassword("123456");
        boolean expected = userOne.equals(registrationService.register(userOne));
        assertTrue(expected);
    }

    @Test
    void register_LoverAgeThanMinAge_notOk() {
        User user = new User();
        user.setAge(MIN_AGE - 1);
        user.setLogin("User");
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("User");
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));

    }

    @Test
    void register_minPasswordLength_ok() {
        User userTwo = new User();
        userTwo.setAge(MIN_AGE);
        userTwo.setLogin("UserTwo");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MIN_PASSWPRD_LENGTH; i++) {
            builder.append(i);
        }
        userTwo.setPassword(builder.toString());
        boolean expected = userTwo.equals(registrationService.register(userTwo));
        assertTrue(expected);
    }

    @Test
    void register_ShorterThanMinLengthPassword_notOk() {
        User user = new User();
        user.setAge(MIN_AGE);
        user.setLogin("User");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MIN_PASSWPRD_LENGTH - 1; i++) {
            builder.append(i);
        }
        user.setPassword(builder.toString());
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_notOk() {
        User user = new User();
        user.setAge(MIN_AGE);
        user.setLogin("User");
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_NotExistLogin_ok() {
        User userThree = new User();
        userThree.setAge(MIN_AGE);
        userThree.setLogin("UserThree");
        userThree.setPassword("123456");
        boolean expected = userThree.equals(registrationService.register(userThree));
        assertTrue(expected);
    }

    @Test
    void register_ExistLogin_notOk() {
        User user = new User();
        user.setAge(MIN_AGE);
        user.setLogin("User");
        user.setPassword("123456");
        registrationService.register(user);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(MIN_AGE);
        user.setLogin(null);
        user.setPassword("123456");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }
}
