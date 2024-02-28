package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private RegistrationService registration = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("pechivo");
        user.setPassword("dfhdkkd");
        user.setAge(20);
    }

    @Test
    void registerNewLogin_Ok() {
        user.setLogin("Karavadjo");
        registration.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertNotNull(user.getId());
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void registerNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registerExistingLogin_NotOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });

    }

    @Test
    void registerNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registerPasswordWithSixCharacters_Ok() {
        user.setPassword("hfkdyt");
        registration.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertNotNull(user.getId());
    }

    @Test
    void registerPasswordWithTenCharacters_Ok() {
        user.setPassword("pojyasdfvb");
        registration.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertNotNull(user.getId());
    }

    @Test
    void registerPasswordWithThreeCharacters_NotOk() {
        user.setPassword("poj");
        assertThrows(RegistrationException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registerNullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registerAgeTen_NotOk() {
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void registerAgeEighteen_Ok() {
        user.setAge(18);
        registration.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertNotNull(user.getId());
    }

    @Test
    void registerAgeThirty_Ok() {
        user.setAge(30);
        registration.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
        assertNotNull(user.getId());
    }

    @Test
    void registerAgeNegative_NotOk() {
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> {
            registration.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
