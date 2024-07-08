package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @BeforeEach
    void setUP() {
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_loginLessThanMinLength_notOk() {
        User user = new User();
        user.setLogin("Bob");
        user.setPassword("ghjkl543");
        user.setAge(23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAllInvalidFields_notOk() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword("ghjkl");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_withTheSameFields_notOk() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword("yxcvb123");
        user.setAge(18);
        Storage.people.add(user);
        User newUser = new User();
        newUser.setLogin("Micael");
        newUser.setPassword("yxcvb123");
        newUser.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_withAgeLessThenMin_notOk() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword("ghjkl1234");
        user.setAge(12);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_with_ExactMinAge_ok() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword("ghjkl1234");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_withLogin_null_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("ghjkl1234");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessThanMin_notOk() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword("ghj");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordExactlyMinLength_ok() {
        User user = new User();
        user.setLogin("Michael");
        user.setPassword("ghjk11");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_userWithAllValidFields_Ok() {
        User user = new User();
        user.setLogin("Micael");
        user.setPassword("ghjkl1234");
        user.setAge(25);
        User actualUser = registrationService.register(user);
        assertNotNull(actualUser);
        assertEquals(user.getLogin(), actualUser.getLogin());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getAge(), actualUser.getAge());
    }
}
