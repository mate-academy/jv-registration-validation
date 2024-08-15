package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.RegistrationFailed;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int CORRECT_AGE = 19;
    private static final int AGE_FOR_TEST = 20;
    private static final int INCORRECT_AGE = 16;
    private static final String INCORRECT_LOGIN = "test";
    private static final String CORRECT_PASSWORD = "testpassword";
    private static final String INNCORRECT_PASSWORD = "qwe";
    private static final String SAME_TEST_LOGIN = "testlogin123";
    private RegistrationServiceImpl registrationService;
    private User testUser;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
        user1 = new User();
        user2 = new User();
    }

    @Test
    void userWithLoginNull_isNotOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationFailed.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void userWithPasswordNull_isNotOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void userWithAgeNull_isNotOk() {
        testUser.setAge(null);
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void loginLengthMustBeGreaterThanSixCharacters() {
        testUser.setLogin(INCORRECT_LOGIN);
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void passwordLengthMustBeGreaterThanSixCharacters() {
        testUser.setPassword(INNCORRECT_PASSWORD);
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void ageMustBeGreaterThanEighteen() {
        testUser.setAge(INCORRECT_AGE);
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void registrationWithTheSameLogin_IsNotOk() {
        user1.setLogin(SAME_TEST_LOGIN);
        user1.setPassword(CORRECT_PASSWORD);
        user1.setAge(CORRECT_AGE);

        user2.setLogin(SAME_TEST_LOGIN);
        user2.setPassword(CORRECT_PASSWORD);
        user2.setAge(AGE_FOR_TEST);

        registrationService.register(user1);

        assertThrows(RegistrationFailed.class, () -> registrationService.register(user2));
    }
}
