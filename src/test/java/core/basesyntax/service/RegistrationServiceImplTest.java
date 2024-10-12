package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();

    @Test
    void register_checkOnUserRegistration_isOk() {
        User actual = new User();
        actual.setLogin("Snowman");
        actual.setPassword("123456");
        actual.setAge(44);
        User expected = REGISTRATION_SERVICE.register(actual);
        assertEquals(expected, actual);
    }

    @Test
    void register_theSameLogin_NotOk() {
        User testUser = new User();
        testUser.setLogin("WormJim");
        testUser.setPassword("123456");
        testUser.setAge(29);
        REGISTRATION_SERVICE.register(testUser);
        User testUser2 = new User();
        testUser2.setLogin("WormJim");
        testUser2.setPassword("654321");
        testUser2.setAge(24);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser2);
        });
    }

    @Test
    void register_invalidLoginLength_notOk() {
        User testUser = new User();
        testUser.setLogin("Jo");
        testUser.setPassword("123456");
        testUser.setAge(24);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setLogin("");
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setLogin("1");
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        User testUser = new User();
        testUser.setLogin("Snowman");
        testUser.setPassword("123");
        testUser.setAge(32);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setPassword("");
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        User testUser = new User();
        testUser.setLogin("Snowman");
        testUser.setPassword("123456");
        testUser.setAge(17);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setAge(-1);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setAge(0);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
    }

    @Test
    void register_nullValue_notOk() {
        User testUser = new User();
        testUser.setLogin(null);
        testUser.setPassword("123456");
        testUser.setAge(18);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setLogin("Snowman");
        testUser.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        testUser.setPassword("123456");
        testUser.setAge(null);
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(null);
        });
    }
}
