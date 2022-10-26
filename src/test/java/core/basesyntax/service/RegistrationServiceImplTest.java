package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INVALID_AGE = 17;
    private static final String INVALID_SMALL_PASSWORD = "12345";
    private static final int VALID_AGE = 19;
    private static final String VALID_NEW_LOGIN = "Kittycat@gmail.com";
    private static final String VALID_PASSWORD = "67hgj78";
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void register_nullAge_isNotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_smallAge_isNotOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_existingLogin_isNotOk() {
        user.setLogin(user.getLogin());
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullPassword_isNotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_smallPassword_isNotOk() {
        user.setPassword(INVALID_SMALL_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_notAllUserdataIsExcepted_isNotOk() {
        user.setAge(VALID_AGE);
        user.setPassword(INVALID_SMALL_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullLogin_isNotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_validUser_isOk() {
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(VALID_NEW_LOGIN);
        User registeredUser = storageDao.add(user);
        assertEquals(user, registeredUser);
        assertEquals(1, Storage.people.size());
    }
}
