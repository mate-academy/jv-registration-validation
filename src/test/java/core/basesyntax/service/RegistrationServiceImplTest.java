package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INVALID_AGE = 17;
    private static final String INVALID_SMALL_PASSWORD = "12345";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int VALID_AGE = 19;
    private static final String VALID_LOGIN = "Kittycat@gmail.com";
    private static final String VALID_NEW_LOGIN = "Helloworld@gmail.com";
    private static final String VALID_PASSWORD = "67hgj78";
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
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
    void register_nullLogin_isNotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_existingLogin_isNotOk() {
        User registeredUser = storageDao.add(user);
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            registrationServiceImpl.register(user);
        });
        assertEquals("This login is already exist", thrown.getMessage());
    }

    @Test
    void register_nullPassword_isNotOk() {
        user.setLogin(VALID_NEW_LOGIN);
        user.setPassword(null);
        Throwable thrown1 = assertThrows(RuntimeException.class, () -> {
            registrationServiceImpl.register(user);
        });
        assertEquals("Password can't be null", thrown1.getMessage());
    }

    @Test
    void register_smallPassword_isNotOk() {
        user.setLogin(VALID_NEW_LOGIN);
        user.setPassword(INVALID_SMALL_PASSWORD);
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            registrationServiceImpl.register(user);
        });
        assertEquals("Password length should be "
                + MIN_PASSWORD_LENGTH + " or more symbols", thrown.getMessage());
    }

    @Test
    void register_testUser_isOk() {
        User testUser = new User();
        testUser.setAge(VALID_AGE);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setLogin(VALID_NEW_LOGIN);
        User registeredUser = registrationServiceImpl.register(testUser);
        assertEquals(2, Storage.people.size());
    }
}
