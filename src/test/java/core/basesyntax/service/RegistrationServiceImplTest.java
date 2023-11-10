package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl storageDao;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {

        User bob = new User("bob123", "password1", 25);
        User alice = new User("alice123", "password2", 23);
        User john = new User("john123", "password3", 27);
        User steven = new User("stiven123", "password4", 21);

        Storage.people.add(bob);
        Storage.people.add(alice);
        Storage.people.add(john);
        Storage.people.add(steven);
    }

    @Test
    void register_UserIfNull_NotOk() {
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_LoginOfUserIfInvalid_NotOk() {
        User mike = new User("mike3", "password5", 19);
        User mike1 = new User(null, "password5", 19);
        User mike2 = new User("", "password5", 19);
        User mike3 = new User("m", "password5", 19);
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike1);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike2);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike3);
        });
    }

    @Test
    void register_PasswordOfUserIfInvalid_NotOk() {
        User mike = new User("mike366", "pass", 19);
        User mike1 = new User("mike366", null, 19);
        User mike2 = new User("mike366", "", 19);
        User mike3 = new User("mike366", "p", 19);

        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike1);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike2);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike3);
        });
    }

    @Test
    void register_AgeOfUserIfInvalid_NotOk() {
        User mike2 = new User("mike366", "password1", 17);
        User mike3 = new User("mike366", "password1", -17);
        User mike4 = new User("mike366", "password1", 0);
        User mike5 = new User("mike366", "password1", 150);
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike2);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike3);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike4);
        });
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(mike5);
        });
    }

    @Test
    void register_UserExist_NotOk() {
        User steven = new User("stiven123", "password4", 21);
        Assert.assertThrows(RuntimeException.class, () -> {
            registrationService.register(steven);
        });
    }

    @Test
    void register_RegisterUser_Ok() {
        User user = new User("user123", "password466", 30);
        User actual = registrationService.register(user);
        Assert.assertEquals(actual, user);

    }
}
