package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user;
    private StorageDaoImpl storageDao;


    @BeforeEach
    public void setUp() throws Exception {
        user = new User();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullUser_notOk() {
        User actual = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectAge_notOk() {
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUserCase_OK() {
        User expected = new User(0L, "Alice", "password", 20);
        User actual = registrationService.register(expected);
        assertTrue(expected.equals(actual));
    }

    @Test
    void register_storageContainsNewUser_OK() {
        User actual = new User(0L, "Alice", "password", 20);
        Storage.people.add(actual);
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsLessThanSixSymbols_notOK() {
        User actual = new User(1L, "user1", "one", 20);
        assertNull(registrationService.register(actual));
    }

    @Test
    void register_UserWithThisLoginAlreadyExists_OK() {
        User actual = new User(0L, "User", "password", 21);
        storageDao.add(new User(1L, "User", "1234567", 45));
        assertNull(registrationService.register(actual));
    }

    @Test
    void register_userWithSameLoginExists() {
        storageDao.add(new User(1L, "User", "1234567", 45));
        boolean actual = registrationService.userWithSameLoginExists(new User(2L, "User", "password", 45));
        assertTrue(actual);
    }
}
