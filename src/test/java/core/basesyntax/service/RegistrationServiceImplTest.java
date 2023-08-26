package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("pechivo");
        user.setPassword("dfhdkkd");
        user.setAge(20);
    }

    @Test
    void register_NewLogin_Ok() {
        user.setLogin("Karavadjo");
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ExistingLogin_NotOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordWithSixCharacters_Ok() {
        user.setPassword("hfkdyt");
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordWithTenCharacters_Ok() {
        user.setPassword("pojyasdfvb");
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordWithThreeCharacters_NotOk() {
        user.setPassword("poj");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeOfTen_NotOk() {
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeEighteen_Ok() {
        user.setAge(18);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_AgeThirty_Ok() {
        user.setAge(30);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_AgeNegative_NotOk() {
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
