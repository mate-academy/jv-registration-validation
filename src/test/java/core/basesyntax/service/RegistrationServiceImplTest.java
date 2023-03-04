package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "jessy";
    private static final String VALID_PASSWORD = "0123467";
    private static final String INVALID_PASSWORD = "012346";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;

    @BeforeEach
    void emptyStorage() {
        Storage.people.clear();
    }

    @Test
    void nullInput_notOk() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = null;
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void nullLogin_notOk() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = userConstructor(null,
                VALID_PASSWORD,
                VALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void nullPassword_notOk() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = userConstructor(VALID_LOGIN,
                null,
                VALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void userAlreadyExists_notOk() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = userConstructor(VALID_LOGIN,
                VALID_PASSWORD,
                VALID_AGE);
        registrationService.register(input);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void userTooYoung_notOk() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = userConstructor(VALID_LOGIN,
                VALID_PASSWORD,
                INVALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void invalidPassword_notOk() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = userConstructor(VALID_LOGIN,
                INVALID_PASSWORD,
                INVALID_AGE);
        assertThrows(InvalidInputExcepton.class, () -> {
            registrationService.register(input);
        });
    }

    @Test
    void register_ok() {
        RegistrationService registrationService =
                new RegistrationServiceImpl();
        User input = userConstructor(VALID_LOGIN,
                VALID_PASSWORD,
                VALID_AGE);
        registrationService.register(input);
        assertEquals(new StorageDaoImpl().get(VALID_LOGIN),
                input);
    }

    private User userConstructor(String login,
                                 String password,
                                 int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
