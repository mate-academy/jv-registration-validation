package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final int DEFAULT_AGE = 18;
    private static final int NOT_VALID_AGE = 17;
    private static final int NEGATIVE_NUMBER = -1;
    private static RegistrationService registrationService;
    private static User firstUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setLogin(DEFAULT_LOGIN);
        firstUser.setPassword(DEFAULT_PASSWORD);
        firstUser.setAge(DEFAULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullUserLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_onlyWhitespaceInLogin_notOk() {
        firstUser.setLogin("      ");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_nullUserPassword_notOk() {
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_notValidPassword_notOk() {
        firstUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_onlyWhitespaceInPassword_notOk() {
        firstUser.setPassword("      ");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_nullUserAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_notValidAge_notOk() {
        firstUser.setAge(NOT_VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_negativeAge_notOk() {
        firstUser.setAge(NEGATIVE_NUMBER);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_registerSameUser_notOk() {
        registrationService.register(firstUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_multipleRegistrations_ok() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(new User());
            users.get(i).setLogin(DEFAULT_LOGIN + "_" + i + 1);
            users.get(i).setPassword(DEFAULT_PASSWORD + "_" + i + 1);
            users.get(i).setAge(DEFAULT_AGE + i + 1);
        }
        for (User user : users) {
            assertDoesNotThrow(() -> registrationService.register(user));
        }
    }

    @Test
    void register_multipleSameRegistrationWithSame_notOk() {
        Storage.people.add(firstUser);
        User secondUser = new User();
        secondUser.setLogin(DEFAULT_LOGIN + "_" + 1);
        secondUser.setPassword(DEFAULT_PASSWORD + "_" + 1);
        secondUser.setAge(DEFAULT_AGE + 1);
        Storage.people.add(secondUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
