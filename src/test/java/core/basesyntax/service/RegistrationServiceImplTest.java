package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("validlogin@gmail.com");
        user.setPassword("valid_password");
        user.setAge(18);
    }

    @Test
    void register_validUser_ok() {
        assertTrue(Storage.people.contains(registrationService.register(user)),
                "Users should be equal.");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nonValidUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setLogin("affasdfs.com");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setLogin("@gmail.com");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nonValidUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setPassword("noVal");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nonValidUserAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        Storage.people.add(user);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }
}
