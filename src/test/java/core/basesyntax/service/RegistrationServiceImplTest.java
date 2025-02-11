package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User userOk;
    private static User userNull;
    private static User userLoginNull;
    private static User userLoginThreeLength;
    private static User userLoginFiveLength;
    private static User userLoginSixLength;
    private static User userLoginSevenLength;
    private static User userPasswordNull;
    private static User userPasswordThreeLength;
    private static User userPasswordFiveLength;
    private static User userPasswordSixLength;
    private static User userPasswordSevenLength;
    private static User userAgeNull;
    private static User userAgeNegative;
    private static User userAgeUnderEighteen;
    private static User userAgeEighteen;
    private static User userAgeOverEighteen;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        userOk = new User("Andrew123", "Andrew123", 18);
        userNull = null;
        userLoginNull = new User(null, "Andrew123", 18);
        userLoginThreeLength = new User("And", "Andrew123", 18);
        userLoginFiveLength = new User("Andre", "Andrew123", 18);
        userLoginSixLength = new User("Andrew", "Andrew123", 18);
        userLoginSevenLength = new User("Andrew1", "Andrew123", 18);
        userPasswordNull = new User("Andrew123", null, 18);
        userPasswordThreeLength = new User("Andrew123", "And", 18);
        userPasswordFiveLength = new User("Andrew123", "Andre", 18);
        userPasswordSixLength = new User("Andrew123", "Andrew", 18);
        userPasswordSevenLength = new User("Luck123", "Andrew1", 18);
        userAgeNull = new User("Andrew123", "Andrew123", null);
        userAgeNegative = new User("Andrew123", "Andrew123", -1);
        userAgeUnderEighteen = new User("Andrew123", "Andrew123", 16);
        userAgeEighteen = new User("Andrew123", "Andrew123", 18);
        userAgeOverEighteen = new User("Luck123", "Andrew123", 50);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userNull);
        });
    }

    @Test
    void register_userInStorage_notOk() {
        Storage.people.add(userOk);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userOk);
        });
    }

    @Test
    void register_userInStorage_Ok() {
        assertEquals(userOk, registrationService.register(userOk));
        assertTrue(Storage.people.contains(userOk));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userLoginNull);
        });
    }

    @Test
    void register_invalidLoginLength_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userLoginThreeLength);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userLoginFiveLength);
        });
    }

    @Test
    void register_validLoginLength_Ok() {
        assertEquals(userLoginSixLength, registrationService.register(userLoginSixLength));
        assertTrue(Storage.people.contains(userLoginSixLength));
        assertEquals(userLoginSevenLength, registrationService.register(userLoginSevenLength));
        assertTrue(Storage.people.contains(userLoginSevenLength));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userPasswordNull);
        });
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userPasswordThreeLength);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userPasswordFiveLength);
        });
    }

    @Test
    void register_validPasswordLength_Ok() {
        assertEquals(userPasswordSixLength,
                registrationService.register(userPasswordSixLength));
        assertTrue(Storage.people.contains(userPasswordSixLength));
        assertEquals(userPasswordSevenLength,
                registrationService.register(userPasswordSevenLength));
        assertTrue(Storage.people.contains(userPasswordSevenLength));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userAgeNull);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userAgeNegative);
        });
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userAgeUnderEighteen);
        });
    }

    @Test
    void register_validAge_Ok() {
        assertEquals(userAgeEighteen, registrationService.register(userAgeEighteen));
        assertTrue(Storage.people.contains(userAgeEighteen));
        assertEquals(userAgeOverEighteen, registrationService.register(userAgeOverEighteen));
        assertTrue(Storage.people.contains(userAgeOverEighteen));
    }
}
