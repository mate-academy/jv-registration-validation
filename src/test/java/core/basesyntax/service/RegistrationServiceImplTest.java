package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("loogiin");
        user.setPassword("password");
        user.setAge(20);
    }

    @Test
    void register_Ok() {
        assertTrue(registrationService.register(user) == user);
    }

    @Test
    void register_badInputData() {
        User userNotOk = new User();
        userNotOk.setLogin("s");
        userNotOk.setPassword("s");
        userNotOk.setAge(10);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUser() {
        User nullUser = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_Exists() {
        User sameUser = user;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(sameUser);
        });
    }

    @AfterAll
    static void afterAll() {
        registrationService = new RegistrationServiceImpl();
    }
}
