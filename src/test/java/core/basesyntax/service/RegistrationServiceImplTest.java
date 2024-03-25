package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private final String validLogin = "retkinf";
    private final String validPassword = "qwerty";
    private final int validAge = 18;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin(validLogin);
        validUser.setPassword(validPassword);
        validUser.setAge(validAge);
    }

    @AfterEach
    void clearList() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin("");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword("");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword("abcdf");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("abc");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin("Dmitr");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setLogin("Dmi");
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_youngAge_notOk() {
        validUser.setAge(8);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setAge(-8);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(validUser);
        assertThrows(RegistationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_user_Ok() throws RegistationException {
        registrationService.register(validUser);
        User validUser2 = new User();
        validUser2.setLogin("Artem234");
        validUser2.setPassword("qwerty12fgh");
        validUser2.setAge(32);
        registrationService.register(validUser2);
        assertEquals(validUser, storageDao.get(validLogin));
        assertEquals(validUser2, storageDao.get("Artem234"));
    }
}
