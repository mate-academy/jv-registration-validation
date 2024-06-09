package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int AGE_OF_TEN = 10;
    private static final int AGE_OF_TWENTY = 20;
    private static final String AVERAGE_LOGIN = "login12345";
    private static final String AVERAGE_PASSWORD = "password12345";
    private static final String SHORT_LOGIN = "ABCD";
    private static final String SHORT_PASSWORD = "j2k";
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void createReference() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void user_alreadyExist() throws RegistrationException {
        User user = new User();
        user.setLogin(AVERAGE_LOGIN);
        user.setPassword(AVERAGE_PASSWORD);
        user.setAge(AGE_OF_TWENTY);
        registrationService.register(user);
        for (int i = 0; i < 5; i++) {
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }

    }

    @Test
    void user_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_nullPassword_notOk() {
        User user = new User();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_age_below_min() {
        User user = new User();
        user.setAge(AGE_OF_TEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_loginLength_notMinimal() {
        User user = new User();
        user.setLogin(SHORT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void user_passwordLength_notMinimal() {
        User user = new User();
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
