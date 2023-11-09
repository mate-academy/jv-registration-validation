package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        User bob = new User("bob123", "password1", 25);
        User alice = new User("alice123", "password2", 23);
        User john = new User("john123", "password3", 27);
        User steven = new User("stiven123", "password4", 21);

        Storage.people.add(bob);
        Storage.people.add(alice);
        Storage.people.add(john);
        Storage.people.add(steven);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerShouldThrowNullPointException() {
        Assert.assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerShouldThrowInvalidDataUserExceptionWhenLoginOfUserNotValid() {
        User mike = new User("mike3", "password5", 19);
        User mike1 = new User(null, "password5", 19);
        User mike2 = new User("", "password5", 19);

        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike);
        });
        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike1);
        });
        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike2);
        });
    }

    @Test
    void registerShouldThrowInvalidDataUserExceptionWhenPasswoordOfUserNotValid() {
        User mike = new User("mike366", "pass", 19);
        User mike1 = new User("mike366", null, 19);
        User mike2 = new User("mike366", "", 19);

        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike);
        });
        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike1);
        });
        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike2);
        });
    }

    @Test
    void registerShouldThrowInvalidDataUserExceptionIfAgeOfUserLeast18Ears() {
        User mike2 = new User("mike366", "password1", 17);
        Assert.assertThrows(InvalidDataUserException.class, () -> {
            registrationService.register(mike2);
        });
    }

    @Test
    void registerShouldThrowRuntimeExceptionIfTheSameUserAlreadyExist() {
        User steven = new User("stiven123", "password4", 21);
        Assert.assertThrows(RuntimeException.class, () -> {
            registrationService.register(steven);
        });
    }

    @Test
    void registerShouldRegisterUser() {
        User user = new User("user123", "password466", 30);
        User actual = registrationService.register(user);
        Assert.assertEquals(actual, user);

    }
}
