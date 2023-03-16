package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User testUser;

    @BeforeAll
    static void initializeVariables() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void initializeVariable() {
        Storage.people.removeAll(Storage.people);
        testUser = new User(1L, "login", "password", 24);
    }

    @Test
    void register_userInNull_isNotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_checkIfContains_isOk() {
        registrationService.register(testUser);
        assertTrue(Storage.people.contains(testUser));
    }

    @Test
    void register_invalidAge_isNotOk() {
        testUser.setAge(10);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_ageIsNull_isNotOk() {
        testUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_invalidPassword_isNotOk() {
        testUser.setPassword("12");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_passwordIsNull_isNotOk() {
        testUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_existingLogin_isNotOk() {
        registrationService.register(testUser);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }
}
