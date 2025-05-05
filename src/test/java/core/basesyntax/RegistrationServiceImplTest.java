package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void registerValidUsers_Ok() {
        User user1 = new User("validLogin", "validPass", 18);
        User user2 = new User("ValidLogin@gmail.com", "Valid_pass98", 18);
        User user3 = new User("validlogin_98", "valid_Pass3", 65);

        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);

        assertNotNull(storageDao.get(user1.getLogin()));
        assertNotNull(storageDao.get(user2.getLogin()));
        assertNotNull(storageDao.get(user3.getLogin()));
    }

    @Test
    void register_nullLogin_NotOk() {
        User userNullLogin = new User(null, "validPass", 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_nullPassword_NotOk() {
        User userNullPassword = new User("validLogin", null, 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_nullAge_NotOk() {
        User userNullAge = new User("validLogin", "validPass", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullAge));
    }

    @Test
    void register_smallAge_NotOk() {
        User userUnderage = new User("validLogin", "validPass", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userUnderage));
    }

    @Test
    void register_shortlogin_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("", "validPass", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("abc", "validPass", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("abcdf", "validPass", 18)));
    }

    @Test
    void register_shortPassword_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "abc", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("validLogin", "abcdf", 18)));
    }

    @Test
    void userIsRegistered_NotOk() {
        User user = new User("validLogin2", "validPass", 60);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
