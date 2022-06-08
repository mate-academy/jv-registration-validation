package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "bob";
    private static final String VALID_PASSWORD = "bob123";
    private static final int VALID_AGE = 42;
    private static final String INVALID_PASSWORD = "admin";
    private static final int INVALID_AGE = 17;

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void initialSetUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    public void register_allCorrect_ok() {
        assertEquals(registrationService.register(user), user);
    }

    @Nested
    public class ExceptionExpectingTests {
        @Test
        public void register_nullUser_notOk() {
            user = null;
        }

        @Test
        public void register_userAlreadyExists_notOk() {
            User user2 = new User();
            user2.setLogin(VALID_LOGIN);
            user2.setPassword(VALID_PASSWORD);
            user2.setAge(VALID_AGE);
            registrationService.register(user2);
            assertThrows(RuntimeException.class, () -> registrationService.register(user));
        }

        @Test
        public void register_loginNull_notOk() {
            user.setPassword(null);
        }

        @Test
        public void register_invalidAge_notOk() {
            user.setAge(INVALID_AGE);
        }

        @Test
        public void register_ageNull_notOk() {
            user.setAge(null);
        }

        @Test
        public void register_invalidPassword_notOk() {
            user.setPassword(INVALID_PASSWORD);
        }

        @Test
        public void register_passwordNull_notOk() {
            user.setPassword(null);
        }

        @AfterEach
        public void expectException() {
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(user);
            }, "RuntimeException expected");
        }
    }
}
