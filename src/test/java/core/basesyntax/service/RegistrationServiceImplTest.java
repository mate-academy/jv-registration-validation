package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid@gmail.com";
    private static final String VALID_PASSWORD = "valid_password";
    private static final Integer MIN_REGISTRATION_AGE = 18;
    private static final String INVALID_PASSWORD = "test1";
    private static final String EMPTY_DATA = " ";

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setAge(MIN_REGISTRATION_AGE);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(user);

        assertEquals(user, actual, "Valid user is not registered!");
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_severalUsers_ok() {
        User[] users = new User[5];

        for (int i = 0; i < users.length; i++) {
            users[i] = new User();
            users[i].setLogin("user" + i + "@gmail.com");
            users[i].setAge(MIN_REGISTRATION_AGE + i);
            users[i].setPassword(VALID_PASSWORD);
            registrationService.register(users[i]);
        }

        for (int i = 0; i < users.length; i++) {
            assertEquals(users[i], Storage.people.get(i));
        }
        assertEquals(users.length, Storage.people.size());
    }

    @Test
    void register_userWithSameLoginIsAlreadyExist_notOk() {
        StorageDao storage = new StorageDaoImpl();
        storage.add(user);

        User userWithSameLogin = new User();
        userWithSameLogin.setLogin(VALID_LOGIN);
        userWithSameLogin.setAge(MIN_REGISTRATION_AGE);
        userWithSameLogin.setPassword(VALID_PASSWORD);

        assertThrows(RuntimeException.class, () -> registrationService.register(userWithSameLogin));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_sameUsers_notOk() {
        StorageDao storage = new StorageDaoImpl();
        User sameUser = user;
        storage.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_emptyUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(new User()));
    }

    @Test
    void register_invalidLogin_notOk() {
        User invalidLoginUser = user;
        invalidLoginUser.setLogin(EMPTY_DATA);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidLoginUser));
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginUser = user;
        nullLoginUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        User invalidPasswordUser = user;
        invalidPasswordUser.setPassword(EMPTY_DATA);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidPasswordUser));

        invalidPasswordUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidPasswordUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = user;
        nullPasswordUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_invalidAge_notOK() {
        User invalidAgeUser = user;
        invalidAgeUser.setAge(MIN_REGISTRATION_AGE - 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidAgeUser));

        invalidAgeUser.setAge(Integer.MAX_VALUE + 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_nullAge_notOk() {
        User nullAgeUser = user;
        nullAgeUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullAgeUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}