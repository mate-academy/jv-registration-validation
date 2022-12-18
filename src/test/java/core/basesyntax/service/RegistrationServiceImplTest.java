package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("login");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_tooShortPassword_notOk() {
        user.setLogin("login");
        user.setPassword("123");
        user.setAge(18);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("login");
        user.setPassword("1234567");
        user.setAge(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_tooYoungAge_notOk() {
        user.setLogin("login");
        user.setPassword("1234567");
        user.setAge(17);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user); });
    }

    @Test
    void register_notAvailableLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin("login");
        storageDao.add(existingUser);
        System.out.println(Storage.people);
        user.setLogin("login");
        user.setPassword("1234567");
        user.setAge(18);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user); });
        Storage.people.clear();
    }

    @Test
    void register_suitableUser_Ok() {
        user.setLogin("login");
        user.setPassword("1234567");
        user.setAge(18);
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
