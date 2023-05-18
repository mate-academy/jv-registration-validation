package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
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
        User newUser = user.clone();
        assertEquals(user, registrationService.register(newUser), "Users should be equal.");
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
    void register_userWithAgeLessThan18_notOk() {
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_checkingCorrectnessOfUserNumbering_Ok() {
        register_validUser_ok();
        User newUser = new User();
        newUser.setLogin("second_valid_login@ukr.net");
        newUser.setPassword("password");
        newUser.setAge(25);
        long actualId = registrationService.register(newUser).getId();
        long expectedId = 2L;
        assertEquals(expectedId, actualId, "Expected ID: " + expectedId + "but was: " + actualId);
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
        StorageDaoImpl.clearIndex();
    }
}
