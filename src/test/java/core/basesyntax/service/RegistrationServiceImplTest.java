package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(23);
        user.setPassword("validPassword");
        user.setLogin("validLogin");
    }

    @Test
    void addAndGetValidUserDirectlyInStorage_Ok() {
        Storage.people.add(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_addAndGetValidUser_Ok() {
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void get_nullLogin_notOk() {
        assertNull(storageDao.get(null));
    }

    @Test
    void get_notExistedLogin_notOk() {
        assertNull(storageDao.get("login"));
    }

    @Test
    void register_ExistedLogin_notOk() {
        User user1 = user;
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_wrongAge_notOk() {
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
