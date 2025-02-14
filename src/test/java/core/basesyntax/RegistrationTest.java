package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User validUser;

    @BeforeAll
    static void setUpAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        validUser = new User();
        validUser.setLogin("login123");
        validUser.setPassword("pass123");
        validUser.setAge(23);
    }

    @Test
    void register_validAllData_ok() {
        User registeredUser = registrationService.register(validUser);

        assertNotNull(registeredUser);
        assertEquals(validUser.getLogin(), registeredUser.getLogin());
        assertEquals(validUser.getPassword(), registeredUser.getPassword());
        assertEquals(validUser.getAge(), registeredUser.getAge());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser));
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser));
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        storageDao.add(validUser);

        User sameLoginUser = new User();
        sameLoginUser.setLogin(validUser.getLogin());
        sameLoginUser.setPassword("NewPassword122tqefsd");
        sameLoginUser.setAge(20);

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(sameLoginUser));
    }

    @Test
    void register_loginMin6Char_ok() {
        validUser.setLogin("123456");
        User registeredUser = registrationService.register(validUser);

        assertEquals(validUser.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_loginTooShort5Char_notOk() {
        validUser.setLogin("12345");

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser));
    }

    @Test
    void register_passwordMin6Char_ok() {
        validUser.setPassword("123456");
        User registeredUser = registrationService.register(validUser);

        assertEquals(validUser.getPassword(), registeredUser.getPassword());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_passwordTooShort5Char_notOk() {
        validUser.setPassword("12345");

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser));
    }

    @Test
    void register_userAge18_ok() {
        validUser.setAge(18);
        User registeredUser = registrationService.register(validUser);

        assertEquals(18, registeredUser.getAge());
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_userAgeUnder17_notOk() {
        validUser.setAge(17);

        assertThrows(RegistrationException.class, ()
                -> registrationService.register(validUser));
    }
}
