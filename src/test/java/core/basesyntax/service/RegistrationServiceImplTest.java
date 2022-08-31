package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDao storageDao;
    private User defaultUser;
    private User userWithSameLogin;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        initializeDefaultUser();
        initializeUserWithSameLogin();
    }

    @Test
    void validationUser_Ok() {
        assertFalse(registrationServiceImpl.isValid(new User()), "Incorrect user!");
        boolean actual = registrationServiceImpl.isValid(defaultUser);
        assertTrue(actual);
    }

    @Test
    void register_UserWithCorrectParameters_Ok() {
        registrationServiceImpl.register(defaultUser);
        assertEquals(1, Storage.people.size());
        assertNotNull(storageDao.get(defaultUser.getLogin()));
    }

    @Test
    void register_UserWithSameLogin_NotOk() {
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(userWithSameLogin));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(null));
    }

    @Test
    void register_NullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(defaultUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(defaultUser));
    }

    @Test
    void register_LengthPasswordLessThenExpected_NotOk() {
        defaultUser.setPassword("abc");
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(defaultUser));
    }

    @Test
    void register_UserWithNegativeID_NotOk() {
        defaultUser.setId(-23L);
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(defaultUser));
    }

    @Test
    void register_AgeUserLessThenExpected_NotOk() {
        defaultUser.setAge(17);
        assertThrows(RuntimeException.class, ()
                -> registrationServiceImpl.register(defaultUser));
    }

    private void initializeDefaultUser() {
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setPassword("Password");
        defaultUser.setLogin("Login");
        defaultUser.setAge(19);

    }

    private void initializeUserWithSameLogin() {
        userWithSameLogin = new User();
        userWithSameLogin.setId(23L);
        userWithSameLogin.setPassword("1234567");
        userWithSameLogin.setLogin("Login");
        userWithSameLogin.setAge(67);

    }
}
