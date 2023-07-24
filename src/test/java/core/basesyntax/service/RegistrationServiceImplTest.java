package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "validlogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final int ZERO_AGE = 0;
    private static final int NEGATIVE_AGE = -15;
    private static final int SMALL_AGE = 15;
    private static final String SMALL_LOGIN = "bad";
    private static final String EMPTY_LOGIN = "";
    private static final String FIVE_LETTERS_LOGIN = "worst";
    private static final String SMALL_PASSWORD = "bad";
    private static final String EMPTY_PASSWORD = "";
    private static final String FIVE_LETTERS_PASSWORD = "worst";
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    User validUserSetUp() {
        User actualUser = new User();
        actualUser.setAge(VALID_AGE);
        actualUser.setLogin(VALID_LOGIN);
        actualUser.setPassword(VALID_PASSWORD);
        return actualUser;
    }

    @Test
    void register_validUser_isOk() {
        User actualUser = validUserSetUp();
        Storage.people.clear();
        assertNotNull(registrationService.register(actualUser));
    }

    @Test
    void register_smallLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(SMALL_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_fiveLettersLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(FIVE_LETTERS_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(EMPTY_LOGIN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_smallPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(SMALL_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_fiveLettersPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(FIVE_LETTERS_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(EMPTY_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_zeroAge_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setAge(ZERO_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_smallAge_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setAge(SMALL_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @Test
    void register_existingUser_notOk() {
        User actualUser = validUserSetUp();
        Storage.people.add(actualUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
