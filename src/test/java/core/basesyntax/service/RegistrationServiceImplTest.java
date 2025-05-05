package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final String STRING_EMPTY = new String();
    private static User userTest;

    @Test
    void register_nullLogin_notOk() {
        userTest = new User(null, null, "1234567", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_checkEmptyLogin_notOk() {
        userTest = new User(null, STRING_EMPTY, "1234567", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_nullPassword_notOk() {
        userTest = new User(null, "12345", null, 20);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_checkEmptyPassword_notOk() {
        userTest = new User(null, "12345", STRING_EMPTY, 20);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_checkLengthPassword_notOk() {
        userTest = new User(null, "12345", "123", 20);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_nullAge_notOk() {
        userTest = new User(null, "12345", "123456", null);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_checkMinusAge_notOk() {
        userTest = new User(null, "12345", "123456", -5);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_checkLessMinimumAge_notOk() {
        userTest = new User(null, "12345", "123456", 17);
        assertThrows(ValidationException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_checkAge_Ok() {
        userTest = new User(null, "12345", "123456", 20);
        User userResult = registrationService.register(userTest);
        assertEquals(storageDao.get(userResult.getLogin()),userTest);
    }

    @Test
    void register_nullGetLogin_notOk() {
        userTest = new User(null, "12345", "123456", 20);
        User userResult = registrationService.register(userTest);
        assertEquals(storageDao.get(userResult.getLogin()),userTest);
        User user2 = new User(null, "12345", "123456", 21);
        assertThrows(ValidationException.class, () -> registrationService.register(user2));
    }

    @AfterEach
    void storage_clear() {
        if (!Storage.people.isEmpty()) {
            Storage.people.clear();
        }
    }
}
