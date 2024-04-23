package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private final String validLogin = "vlogin";
    private final String validPassword = "valpas";
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
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin("qwe");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setLogin("qwecb");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword("asd");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("asdfn");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(validUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_ageNull_notOk() {
        validUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_ageLessAllowed_notOk() {
        validUser.setAge(10);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setAge(-10);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_user_ok() throws RegistrationException {
        registrationService.register(validUser);
        User validUser2 = new User();
        validUser2.setLogin("logini");
        validUser2.setPassword("password");
        validUser2.setAge(40);
        registrationService.register(validUser2);
        assertEquals(validUser, storageDao.get(validLogin));
        assertEquals(validUser2, storageDao.get("logini"));
    }
}
