package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exaption.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User testUser;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();

    }

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
    void addUserIsRegister_ThrowsRegistrationException_Ok() {
        registrationService.register(testUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserPasswordLessThen6_ThrowsRegistrationException_notOk() {
        testUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserInfoIsNull_ThrowsRegistrationException_notOk() {
        testUser.setPassword(null);
        testUser.setAge(null);
        testUser.setId(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserLoginLessThen6_ThrowsRegistrationException_notOk() {
        testUser.setLogin("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void addUserAgeLessThen18_ThrowsRegistrationException_notOk() {
        testUser.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_UserAlreadyExists_ThrowsRegistrationException_notOk() {
        registrationService.register(testUser);

        User duplicateUser = new User();
        duplicateUser.setAge(25);
        duplicateUser.setLogin("QWERTY123");
        duplicateUser.setPassword("duplicatePassword_123");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(duplicateUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
