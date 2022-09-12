package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
    void registrationNormal_OK() {
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void registrationAgeMax_OK() {
        bobFirst.setAge(Integer.MAX_VALUE);
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void registrationNameNull_NotOk() {
        bobFirst.setLogin(null);
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("Null cannot be registered because it is not a name");
    }

    @Test
    void registrationNameEmpty_NotOk() {
        bobFirst.setLogin("");
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("An empty name cannot be registered");
    }

    @Test
    void registrationPasswordNull_NotOk() {
        bobFirst.setPassword(null);
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("Null cannot be a password in user");
    }

    @Test
    void registrationPasswordEmpty_NotOk() {
        bobFirst.setPassword("");
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("Empty cannot be a password in user");
    }

    @Test
    void registrationPasswordSmall_NotOk() {
        bobFirst.setPassword("12345");
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("Small password in user");
    }

    @Test
    void registrationAge0_NotOk() {
        bobFirst.setAge(0);
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("age cannot be less than 18");
    }

    @Test
    void registrationAgeNegative_NotOk() {
        bobFirst.setAge(Integer.MIN_VALUE);
        try {
            User user = registrationService.register(bobFirst);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("age cannot be less than 18");
    }

    @Test
    void registrationTwoDifferentBobs_OK() {
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
        User user1 = registrationService.register(bobSecond);
        assertEquals(bobSecond, user1);
        assertEquals(Storage.people.size(), 2);
    }

    @Test
    void registrationIdenticalBobs_NotOK() {
        User user = registrationService.register(bobFirst);
        assertEquals(bobFirst, user);
        assertEquals(Storage.people.size(), 1);
        try {
            User user1 = registrationService.register(bobFirst);

        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 1);
            return;
        }
        fail("You cannot register a user again with the same data!");
    }

    @Test
    void registrationNotInitialized_NotOk() {
        try {
            User user = registrationService.register(alice);
        } catch (RuntimeException e) {
            assertEquals(Storage.people.size(), 0);
            return;
        }
        fail("age cannot be less than 18");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
