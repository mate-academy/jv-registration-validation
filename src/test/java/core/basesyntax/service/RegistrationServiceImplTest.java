package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY_STRING = "";
    private static final String ONE_CARECTER_STRING = "A";
    private static final String THRE_CARACTER_STRING = "pAs";
    private static final String FIVE_CARACTER_STRING = "passW";
    private static final int NEGATIVE_AGE = -10;
    private static final int AGE_IS_ZERO = 0;
    private static final int AGE_IS_SEVENTEEN = 17;
    private static final int OVER_AGE_USER = 150;

    private static RegistrationServiceImpl registrationService;

    private User bob;
    private User steven;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        bob = new User("bob123", "password1", 25);
        steven = new User("stiven123", "password4", 21);

    }

    @Test
    void register_UserIfNull_NotOk() {
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_LoginOfUserIfInvalid_NotOk() {
        User mike = new User(FIVE_CARACTER_STRING, "password5", 19);
        User mike1 = new User(null, "password5", 19);
        User mike2 = new User(EMPTY_STRING, "password5", 19);
        User mike3 = new User(ONE_CARECTER_STRING, "password5", 19);
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
        User mike = new User("mike366", THRE_CARACTER_STRING, 19);
        User mike1 = new User("mike366", null, 19);
        User mike2 = new User("mike366", EMPTY_STRING, 19);
        User mike3 = new User("mike366", ONE_CARECTER_STRING, 19);

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
        User mike2 = new User("mike366", "password1", AGE_IS_SEVENTEEN);
        User mike3 = new User("mike366", "password1", NEGATIVE_AGE);
        User mike4 = new User("mike366", "password1", AGE_IS_ZERO);
        User mike5 = new User("mike366", "password1", OVER_AGE_USER);
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
        Storage.people.add(steven);
        Assert.assertThrows(RegistrationException.class, () -> {
            registrationService.register(steven);
        });
    }

    @Test
    void register_RegisterValidUser_Ok() {
        User alice = new User("alice123", "password2", 23);
        User userExp1 = registrationService.register(alice);
        Assertions.assertEquals(userExp1, alice);

    }
}
