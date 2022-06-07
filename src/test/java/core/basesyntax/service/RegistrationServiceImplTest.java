package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin("bob");
        user.setPassword("bob123");
        user.setAge(42);
        user.setId((long) 1234);
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
            user2.setLogin("bob");
            user2.setPassword("alice456");
            user2.setAge(34);
            user2.setId((long) 5678);
            registrationService.register(user2);
        }

        @Test
        public void register_loginNull_notOk() {
            user.setPassword(null);
        }

        @Test
        public void register_ageLessThan18_notOk() {
            user.setAge(17);
        }

        @Test
        public void register_ageNull_notOk() {
            user.setAge(null);
        }

        @Test
        public void register_idNull_notOk() {
            user.setId(null);
        }

        @Test
        public void register_passwordLessThanSixSymbols_notOk() {
            user.setPassword("admin");
        }

        @Test
        public void register_passwordNull_notOk() {
            user.setPassword(null);
        }

        @AfterEach
        public void expectException() {
            assertThrows(RuntimeException.class, () -> {
                registrationService.register(user);
            });
        }
    }
}
