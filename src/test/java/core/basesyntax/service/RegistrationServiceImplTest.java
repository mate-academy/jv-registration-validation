package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.exception.UserNullPointerException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 20;
    private static final String DEFAULT_LOGIN = "loginlogin";
    private static final String DEFAULT_PASSWORD = "password";
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void name() {
    }

    private User setUpUser(int age, String login, String password) {
        User user = new User();

        user.setAge(age);
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }

    @Test
    void register_existedUser_NotOk() {
        User user1 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, DEFAULT_PASSWORD);

        registrationService.register(user1);
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user1));
    }


    @Test
    void register_loginLength_ok() {
        User user1 = setUpUser(DEFAULT_AGE, "longlogin", DEFAULT_PASSWORD);
        User user2 = setUpUser(DEFAULT_AGE, "login6", DEFAULT_PASSWORD);

        Assertions.assertEquals(user1, registrationService.register(user1));
        Assertions.assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_loginLength_NotOk() {
        User user0 = setUpUser(DEFAULT_AGE, null, DEFAULT_PASSWORD);
        User user1 = setUpUser(DEFAULT_AGE, "login", DEFAULT_PASSWORD);
        User user2 = setUpUser(DEFAULT_AGE, "     ", DEFAULT_PASSWORD);
        User user3 = setUpUser(DEFAULT_AGE, "      ", DEFAULT_PASSWORD);
        User user4 = setUpUser(DEFAULT_AGE, "                 ", DEFAULT_PASSWORD);

        Assertions.assertThrows(UserNullPointerException.class, () -> registrationService.register(user0));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user4));
    }

    @Test
    void register_passwordLength_Ok() {
        User user1 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "passwr");
        User user2 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "password");

        Assertions.assertEquals(user1, registrationService.register(user1));
        Assertions.assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_passwordLength_NotOk() {
        User user0 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, null);
        User user1 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "pass");
        User user2 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "    ");
        User user3 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "      ");
        User user4 = setUpUser(DEFAULT_AGE, DEFAULT_LOGIN, "                 ");

        Assertions.assertThrows(UserNullPointerException.class, () -> registrationService.register(user0));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user4));
    }

    @Test
    void register_userAge_Ok() {
        User user1 = setUpUser(18, DEFAULT_LOGIN, DEFAULT_PASSWORD);
        User user2 = setUpUser(124, DEFAULT_LOGIN, DEFAULT_PASSWORD);

        Assertions.assertEquals(user1, registrationService.register(user1));
        Assertions.assertEquals(user2, registrationService.register(user2));
    }

    @Test
    void register_userAge_NotOk() {
        User user1 = setUpUser(17, DEFAULT_LOGIN, DEFAULT_PASSWORD);
        User user2 = setUpUser(2, DEFAULT_LOGIN, DEFAULT_PASSWORD);
        User user3 = setUpUser(-20, DEFAULT_LOGIN, DEFAULT_PASSWORD);

        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_userNull_Ok() {
        Assertions.assertThrows(UserNullPointerException.class, () -> registrationService.register(null));
    }

}