package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        defaultUser = new User();
        defaultUser.setAge(18);
        defaultUser.setLogin("username1");
        defaultUser.setPassword("123456");
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_negativeAge_notOk() {
        defaultUser.setAge(Integer.MIN_VALUE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setAge(-10);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setAge(-1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_underAge_notOk() {
        defaultUser.setAge(0);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setAge(10);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setAge(17);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_sufficientAge_Ok() {
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_smallLogin_notOk() {
        defaultUser.setLogin("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setLogin(" ");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setLogin("q");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setLogin("qwe");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setLogin("qwert");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_normalLogin_Ok() {
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_smallPassword_notOk() {
        defaultUser.setPassword("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setPassword(" ");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setPassword("1");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setPassword("123");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
        defaultUser.setPassword("12345");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void register_normalPassword_Ok() {
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    void register_addingExistingUser_notOk() {
        Storage.people.add(defaultUser);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    void registration_addingUniqueUsers_Ok() {
        User uniqueUser = new User();
        uniqueUser.setLogin("username2");
        uniqueUser.setAge(18);
        uniqueUser.setPassword("123456");
        Assertions.assertEquals(defaultUser, registrationService.register(defaultUser));
        Assertions.assertEquals(uniqueUser, registrationService.register(uniqueUser));
    }
}
