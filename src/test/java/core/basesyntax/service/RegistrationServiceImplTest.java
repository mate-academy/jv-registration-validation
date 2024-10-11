package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();

    @Test
    void register_theSameLogin_NotOk() {
        User testUser = new User();
        testUser.setLogin("WormJim");
        testUser.setPassword("123456");
        testUser.setAge(29);
        User testUser2 = new User();
        testUser2.setLogin("WormJim");
        testUser2.setPassword("654321");
        testUser2.setAge(24);
        User expected = REGISTRATION_SERVICE.register(testUser);
        User actual = REGISTRATION_SERVICE.register(testUser2);
        assertEquals(expected, actual, "Test failed you should not register the same user's login");
    }

    @Test
    void register_invalidLoginLength_notOk() {
        User testUser = new User();
        User testUser2 = new User();
        testUser.setLogin("Jo");
        testUser.setPassword("123456");
        testUser2.setLogin("");
        testUser2.setPassword("123456");
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser2);
        });
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        User testUser = new User();
        User testUser2 = new User();
        testUser.setLogin("Snowman");
        testUser.setPassword("123");
        testUser2.setLogin("Bigfoot");
        testUser2.setPassword("");
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser);
        });
        assertThrows(ValidationException.class, () -> {
            REGISTRATION_SERVICE.register(testUser2);

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
    }
}
