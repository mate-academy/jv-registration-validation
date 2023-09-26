package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("QWERTY123");
        testUser.setAge(21);
        testUser.setPassword("start33Password_007");
    }

    @Test
    void addTrueUser_Ok() {
        assertEquals(registrationService.register(testUser), testUser);
    }

    @Test
    void addUserIsRegister_exception() {
        registrationService.register(testUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserPasswordLessThen6_exception() {
        testUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserInfoIsNull_exception() {
        testUser.setPassword(null);
        testUser.setAge(null);
        testUser.setAge(null);
        testUser.setId(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserLoginLessThen6_exception() {
        testUser.setLogin("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserAgeLessThen18_exception() {
        testUser.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

}
