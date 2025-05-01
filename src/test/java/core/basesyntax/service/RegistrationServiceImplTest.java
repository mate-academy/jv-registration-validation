package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_checkOnUserRegistration_isOk() {
        User actual = new User();
        actual.setLogin("Snowtrack");
        actual.setPassword("1234567");
        actual.setAge(44);
        User actual1 = new User();
        actual1.setLogin("Genadiy");
        actual1.setPassword("1234567");
        actual1.setAge(18);
        User actual2 = new User();
        actual2.setLogin("Nicole");
        actual2.setPassword("1234567");
        actual2.setAge(22);
        User actual3 = new User();
        actual3.setLogin("Snowhunter");
        actual3.setPassword("123456");
        actual3.setAge(41);
        User expected = registrationService.register(actual);
        assertEquals(expected, actual);
        expected = registrationService.register(actual1);
        assertEquals(expected, actual1);
        expected = registrationService.register(actual2);
        assertEquals(expected, actual2);
        expected = registrationService.register(actual3);
        assertEquals(expected, actual3);
    }

    @Test
    void register_theSameLogin_NotOk() {
        User testUser = new User();
        testUser.setLogin("WormJim");
        testUser.setPassword("123456");
        testUser.setAge(29);
        registrationService.register(testUser);
        User testUser2 = new User();
        testUser2.setLogin("WormJim");
        testUser2.setPassword("654321");
        testUser2.setAge(24);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser2);
        });
    }

    @Test
    void register_invalidLoginLength_notOk() {
        User testUser = new User();
        testUser.setLogin("Jo");
        testUser.setPassword("123456");
        testUser.setAge(24);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setLogin("");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setLogin("J");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        User testUser = new User();
        testUser.setLogin("Snowman");
        testUser.setPassword("123");
        testUser.setAge(32);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword("");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword("12345");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        User testUser = new User();
        testUser.setLogin("Snowman");
        testUser.setPassword("123456");
        testUser.setAge(17);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setAge(-1);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setAge(0);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullValue_notOk() {
        User testUser = new User();
        testUser.setLogin(null);
        testUser.setPassword("123456");
        testUser.setAge(18);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setLogin("Snowman");
        testUser.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        testUser.setPassword("123456");
        testUser.setAge(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(testUser);
        });
        assertThrows(ValidationException.class, () -> {
            registrationService.register(null);
        });
    }
}
