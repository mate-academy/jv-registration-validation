package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login");
        user.setPassword("1234567");
        user.setAge(18);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_tooShortPassword_notOk() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_tooYoungAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_notAvailableLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin("login");
        storageDao.add(existingUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user); });
        Storage.people.clear();
    }

    @Test
    void register_suitableUser_Ok() {
        User actualUser = registrationService.register(user);
        User expectedUser = user;
        assertEquals(expectedUser, actualUser);
        int actualStorageSize = Storage.people.size();
        int expectedStorageSize = 1;
        assertEquals(expectedStorageSize, actualStorageSize);
        assertTrue(Storage.people.contains(actualUser));
        assertEquals(expectedUser, storageDao.get(expectedUser.getLogin()));
    }
}
