package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.AlreadyRegistered;
import core.basesyntax.exceptions.ValidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private User testUser;
    private int testUserIterator;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(0L);
        testUser.setLogin("Stray228");
        testUser.setPassword("12345678");
        testUser.setAge(18);
    }

    @Test
    void register_user_Ok() {
        assertDoesNotThrow(() -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setPassword("1234");
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_shortLogin_notOk() {
        testUser.setLogin("Cat");
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAlreadyRegistered_notOk() {
        Storage.people.add(testUser);
        assertThrows(AlreadyRegistered.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_youngAge_notOk() {
        testUser.setAge(5);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAddedToStorage_okay() {
        assertDoesNotThrow(() -> {
            registrationService.register(testUser);
        });
        int testUserIndex = Math.toIntExact(testUser.getId());
        assertEquals(Storage.people.get(testUserIndex), testUser);
    }

    @Test
    void register_isThatExactlyUserAddedToStorage_okay() {
        assertDoesNotThrow(() -> {
            registrationService.register(testUser);
        });
        int indexAdded = Math.toIntExact(testUser.getId()) - 1;
        int receivedExactlyUserId = Math.toIntExact(Storage.people.get(indexAdded).getId()) - 1;
        assertEquals(indexAdded, receivedExactlyUserId);
    }

    @Test
    void register_userNotAddedToStorage_okay() {
        testUser.setAge(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
        try {
            int testUserIndex = Math.toIntExact(testUser.getId()) - 1;
            assertNotEquals(Storage.people.get(testUserIndex), testUser);
        } catch (IndexOutOfBoundsException e) {
            // Nice!
        }
    }

    @AfterEach
    void onTearDown() {
        testUser.setLogin(testUser.getLogin() + " " + testUserIterator);
        testUser.setId(testUser.getId() + 1);
        testUserIterator++;
    }
}
