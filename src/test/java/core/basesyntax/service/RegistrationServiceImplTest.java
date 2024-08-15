package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.RegistrationFailed;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int CORRECT_AGE = 19;
    private static final String CORRECT_LOGIN = "testlogin";
    private static final String CORRECT_PASSWORD = "testpassword";
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
    void correctLoginLenght() {
        testUser.setLogin("321");
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void correctPassword() {
        testUser.setPassword("321");
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void correctAge() {
        testUser.setAge(1);
        assertThrows(RegistrationFailed.class, () -> registrationService.register(testUser));
    }

    @Test
    void registrationWithTheSameLogin_IsNotOk() {
        user1.setLogin(SAME_TEST_LOGIN);
        user1.setPassword(CORRECT_PASSWORD);
        user1.setAge(19);

        user2.setLogin(SAME_TEST_LOGIN);
        user2.setPassword(CORRECT_PASSWORD);
        user2.setAge(25);

        registrationService.register(user1);

        assertThrows(RegistrationFailed.class, () -> registrationService.register(user2));
    }
}
