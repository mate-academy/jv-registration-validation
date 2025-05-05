package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String VALID_PASSWORD = "ValidPassword";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(MIN_AGE);
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        user.setPassword("abc");
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
        user.setPassword("abcde");
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthGreaterThan6_ok() throws UserValidationException {
        User anotherUser = new User();
        anotherUser.setLogin("Another");
        anotherUser.setPassword("abcdef");
        anotherUser.setAge(user.getAge());
        assertEquals(anotherUser, registrationService.register(anotherUser));

        User anotherOneUser = new User();
        anotherOneUser.setLogin(anotherUser.getLogin() + "another");
        anotherOneUser.setId(anotherUser.getId() + 1);
        anotherOneUser.setPassword("abcdefg");
        anotherOneUser.setAge(anotherUser.getAge());
        assertEquals(anotherOneUser, registrationService.register(anotherOneUser));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthLessThan6_notOk() {
        user.setLogin("abc");
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
        user.setLogin("abcde");
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthGreaterThan6_ok() throws UserValidationException {
        user.setLogin("abcdef");
        assertEquals(user, registrationService.register(user));

        User anotherUser = new User();
        anotherUser.setPassword(user.getPassword());
        anotherUser.setId(user.getId() + 1);
        anotherUser.setLogin("abcdefg");
        anotherUser.setAge(user.getAge());
        assertEquals(anotherUser, registrationService.register(anotherUser));
    }

    @Test
    void register_ageIsNotEmpty_notOk() {
        user.setAge(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanMinAge_NotOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageGreaterThanMinAge_ok() throws UserValidationException {
        assertEquals(user, registrationService.register(user));

        User anotherUser = new User();
        anotherUser.setLogin(user.getLogin() + "another");
        anotherUser.setPassword(user.getPassword());
        anotherUser.setId(user.getId() + 1);
        anotherUser.setAge(user.getAge() + 1);
        assertEquals(anotherUser, registrationService.register(anotherUser));
    }

    @Test
    void register_nullUser_notOk() {
        User user = new User();
        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }
}
