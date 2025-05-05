package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "123456", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("Alice", null, 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("Alice", "123456", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User("", "123456", 54);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User("Kate", "", 54);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_ageNegative_notOk() {
        User user = new User("Alice", "123456", -5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_ageZero_notOk() {
        User user = new User("Alice", "123456", 0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User("Alice", "123456", 10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @Test
    void register_age18_Ok() {
        User user = new User("Alice", "123456", 18);
        registrationService.register(user);
        assertEquals(1,Storage.people.size(), "Test failed! Size of storage should be "
                + 1 + "but it is " + Storage.people.size());
        assertTrue(Storage.people.contains(user), "Method register should add user with valid "
                + "parameters");
    }

    @Test
    void register_ageOver18_Ok() {
        User user = new User("Alice", "123456", 75);
        registrationService.register(user);
        assertEquals(1,Storage.people.size(), "Test failed! Size of storage should be "
                + 1 + "but it is " + Storage.people.size());
        assertTrue(Storage.people.contains(user), "Method register should add user with valid "
                + "parameters");
    }

    @Test
    void register_SuccessesRegistration_Ok() {
        User user1 = new User("Andrew", "123456", 27);
        User user2 = new User("Mihail", "123456", 45);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(2,Storage.people.size(), "Test failed! Size of storage should be "
                + 2 + "but it is " + Storage.people.size());
        assertTrue(Storage.people.contains(user1), "Method register should add user with valid "
                + "parameters");
        assertTrue(Storage.people.contains(user2), "Method register should add user with valid "
                + "parameters");
    }

    @Test
    void register_tryRegisterSecondTime_notOk() {
        User user1 = new User("Andrew", "123456", 27);
        User user2 = new User("Andrew", "123456", 27);
        storageDao.add(user1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertTrue(Storage.people.contains(user2), "Method register shouldn't add has already "
                + "been user");
    }

    @Test
    void register_passwordLessThenSixCharacters_notOk() {
        User user = new User("Alice", "12345", 24);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertFalse(Storage.people.contains(user), "Method register shouldn't add user with "
                + "not valid parameters");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
