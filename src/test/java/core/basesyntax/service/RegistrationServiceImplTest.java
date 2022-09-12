package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User bobFirst;
    private User bobSecond;
    private User alice;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        bobFirst = new User();
        bobFirst.setLogin("Bob_First");
        bobFirst.setAge(32);
        bobFirst.setPassword("123456");
        bobSecond = new User();
        bobSecond.setLogin("Bob_Second");
        bobSecond.setAge(31);
        bobSecond.setPassword("123456");
        alice = new User();
    }

    @Test
    void register_Normal_OK() {
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_AgeMax_OK() {
        bobFirst.setAge(Integer.MAX_VALUE);
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_NameNull_NotOk() {
        bobFirst.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_NameEmpty_NotOk() {
        bobFirst.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_PasswordNull_NotOk() {
        bobFirst.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_PasswordEmpty_NotOk() {
        bobFirst.setPassword("");
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_PasswordSmall_NotOk() {
        bobFirst.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_Age0_NotOk() {
        bobFirst.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_AgeNegative_NotOk() {
        bobFirst.setAge(Integer.MIN_VALUE);
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @Test
    void register_TwoDifferentBobs_OK() {
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
        User user1 = registrationService.register(bobSecond);
        assertEquals(bobSecond, user1);
        assertEquals(Storage.people.size(), 2);
    }

    @Test
    void register_IdenticalBobs_NotOK() {
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
        assertThrows(RuntimeException.class, () -> {
            User user1 = registrationService.register(bobFirst);
        });
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_NotInitialized_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(alice);
        });
        assertEquals(Storage.people.size(), 0);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
