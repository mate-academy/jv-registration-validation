package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }

    @Test
    void register_addProperUser_ok() {
        user.setAge(18);
        user.setLogin("cris");
        user.setPassword("123456");
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_addThreeProperUsers_ok() {
        user.setAge(19);
        user.setLogin("boris");
        user.setPassword("123456");
        User secondUser = new User();
        secondUser.setAge(24);
        secondUser.setLogin("morris");
        secondUser.setPassword("1234567");
        User thirdUser = new User();
        thirdUser.setAge(48);
        thirdUser.setLogin("cris");
        thirdUser.setPassword("1234568");

        Assertions.assertEquals(user, registrationService.register(user));
        Assertions.assertEquals(secondUser, registrationService.register(secondUser));
        Assertions.assertEquals(thirdUser, registrationService.register(thirdUser));
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_sameLogin_notOk() {
        user.setAge(24);
        user.setLogin("boris");
        user.setPassword("123456");
        User secondUser = new User();
        secondUser.setAge(18);
        secondUser.setLogin("boris");
        secondUser.setPassword("1234567");
        registrationService.register(user);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setAge(24);
        user.setLogin(null);
        user.setPassword("123456");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageTooYoung_notOk() {
        user.setAge(17);
        user.setLogin("morris");
        user.setPassword("123456");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_age18_Ok() {
        user.setAge(18);
        user.setLogin("morris");
        user.setPassword("123456");
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_ageNegativeNumber_notOk() {
        user.setAge(-1);
        user.setLogin("morris");
        user.setPassword("123456");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageTooOld_notOk() {
        user.setAge(121);
        user.setLogin("boris");
        user.setPassword("123456");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        user.setLogin("cris");
        user.setPassword("123456");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordTooShort_notOk() {
        user.setAge(24);
        user.setLogin("john");
        user.setPassword("123");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordTooLong_notOk() {
        user.setAge(35);
        user.setLogin("mike");
        user.setPassword("12345678901234567890123456789");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setAge(87);
        user.setLogin("philip");
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
