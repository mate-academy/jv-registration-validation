package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private static final String VALID_EDGE_LOGIN = "valLog";
    private static final String VALID_MEDIUM_LOGIN = "valid login";
    private static final String VALID_LONG_LOGIN = "long valid login";
    private static final String VALID_EDGE_PASSWORD = "valPas";
    private static final String VALID_MEDIUM_PASSWORD = "valid password";
    private static final String VALID_LONG_PASSWORD = "long valid password";
    private static final int VALID_EDGE_AGE = 18;
    private static final int VALID_MEDIUM_AGE = 45;
    private static final int VALID_OLD_AGE = 115;
    private static final String FIRST_TOO_SHORT_LOGIN = "c";
    private static final String SECOND_TOO_SHORT_LOGIN = "abc";
    private static final String THIRD_TOO_SHORT_LOGIN = "abcde";
    private static final String FIRST_TOO_SHORT_PASSWORD = "9";
    private static final String SECOND_TOO_SHORT_PASSWORD = "1b3";
    private static final String THIRD_TOO_SHORT_PASSWORD = "abc45";
    private static final int TOO_YOUNG_AGE_17 = 17;
    private static final int TOO_YOUNG_AGE_1 = 1;
    private static final int TOO_YOUNG_AGE_0 = 0;
    private static RegistrationService registrationService;
    private User firstUser;
    private User secondUser;
    private User thirdUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setAge(VALID_EDGE_AGE);
        firstUser.setLogin(VALID_EDGE_LOGIN);
        firstUser.setPassword(VALID_EDGE_PASSWORD);
        secondUser = new User();
        secondUser.setAge(VALID_MEDIUM_AGE);
        secondUser.setLogin(VALID_MEDIUM_LOGIN);
        secondUser.setPassword(VALID_MEDIUM_PASSWORD);
        thirdUser = new User();
        thirdUser.setAge(VALID_OLD_AGE);
        thirdUser.setLogin(VALID_LONG_LOGIN);
        thirdUser.setPassword(VALID_LONG_PASSWORD);
    }

    @Test
    void register_validUser_ok() {
        User firstActualUser = registrationService.register(firstUser);
        User secondActualUser = registrationService.register(secondUser);
        User thirdActualUser = registrationService.register(thirdUser);
        Assertions.assertEquals(firstUser, firstActualUser);
        Assertions.assertEquals(secondUser, secondActualUser);
        Assertions.assertEquals(thirdUser, thirdActualUser);
    }

    @Test
    void register_userWithExistLogin_notOk() {
        secondUser.setLogin(firstUser.getLogin());
        Storage.people.add(secondUser);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "There is shouldn't add a user with already exist login in the storage. Login: "
                + firstUser.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                "There is shouldn't to pass null User");
    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "There is shouldn't to pass null login");
    }

    @Test
    void register_shortLogin_notOk() {
        firstUser.setLogin(FIRST_TOO_SHORT_LOGIN);
        secondUser.setLogin(SECOND_TOO_SHORT_LOGIN);
        thirdUser.setLogin(THIRD_TOO_SHORT_LOGIN);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "Login should be least " + MIN_LOGIN_LENGTH + "characters, but: "
                + FIRST_TOO_SHORT_LOGIN.length());
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(secondUser),
                "Login should be least " + MIN_LOGIN_LENGTH + "characters, but: "
                        + SECOND_TOO_SHORT_LOGIN.length());
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(thirdUser),
                "Login should be least " + MIN_LOGIN_LENGTH + "characters, but: "
                        + THIRD_TOO_SHORT_LOGIN.length());
    }

    @Test
    void register_nullPassword_notOk() {
        firstUser.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "There is shouldn't to pass null password");
    }

    @Test
    void register_shortPassword_notOk() {
        firstUser.setPassword(FIRST_TOO_SHORT_PASSWORD);
        secondUser.setPassword(SECOND_TOO_SHORT_PASSWORD);
        thirdUser.setPassword(THIRD_TOO_SHORT_PASSWORD);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "Password should be least " + MIN_PASSWORD_LENGTH + "characters, but: "
                + FIRST_TOO_SHORT_PASSWORD.length());
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(secondUser),
                "Password should be least " + MIN_PASSWORD_LENGTH + "characters, but: "
                        + SECOND_TOO_SHORT_PASSWORD.length());
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(thirdUser),
                "Password should be least " + MIN_PASSWORD_LENGTH + "characters, but: "
                        + THIRD_TOO_SHORT_PASSWORD.length());
    }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "There is shouldn't to pass null age");
    }

    @Test
    void register_youngAge_notOk() {
        firstUser.setAge(TOO_YOUNG_AGE_0);
        secondUser.setAge(TOO_YOUNG_AGE_1);
        thirdUser.setAge(TOO_YOUNG_AGE_17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(firstUser),
                "Age should be least " + MIN_USER_AGE + ", but: " + TOO_YOUNG_AGE_0);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(secondUser),
                "Age should be least " + MIN_USER_AGE + ", but: " + TOO_YOUNG_AGE_1);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(thirdUser),
                "Age should be least " + MIN_USER_AGE + ", but: " + TOO_YOUNG_AGE_17);

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
