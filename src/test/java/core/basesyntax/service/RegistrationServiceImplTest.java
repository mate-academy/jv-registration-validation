package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao;
    private RegistrationService registrationService;
    private User defaultUser;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        defaultUser = new User();
        defaultUser.setLogin("testLogin");
        defaultUser.setPassword("testPassword");
        defaultUser.setAge(33);
    }

    @Test
    void register_returnValidUser_ok() {
        registrationService.register(defaultUser);

        User savedUser = storageDao.get(defaultUser.getLogin());
        assertNotNull(storageDao.get(defaultUser.getLogin()), "User should be added to storage");
        assertEquals(defaultUser, savedUser, "Returned user should be the same as user added");
    }

    @Test
    void register_loginIsUnique_ok() {
        assertNull(storageDao.get(defaultUser.getLogin()), "Login must be unique");
        Storage.people.add(defaultUser);
        User savedUser = storageDao.get(defaultUser.getLogin());
        assertNotNull(storageDao.get(defaultUser.getLogin()));
        assertEquals(defaultUser, savedUser,
                "User saved to storage must be the same as User was created");
    }

    @Test
    void register_loginExists_notOk() {
        User uniqueUser = defaultUser;
        Storage.people.add(uniqueUser);

        User notUniqueUser = defaultUser;
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(notUniqueUser),
                "Registration should throw Custom Exception when login already exists");
    }

    @Test
    void register_loginLength_ok() {
        User user = defaultUser;
        Storage.people.add(user);
        User savedUser = storageDao.get(user.getLogin());

        assertTrue(user.getLogin().length() >= MIN_LOGIN_LENGTH,
                "Login must be at least "
                        + MIN_LOGIN_LENGTH
                        + " characters long"
        );
        assertEquals(user, savedUser, "User login must be saved to storage");
    }

    @Test
    void register_loginLengthIsShort_notOk() {
        defaultUser.setLogin("art");

        assertFalse(defaultUser.getLogin().length() >= MIN_LOGIN_LENGTH,
                "Login must be at least "
                        + MIN_LOGIN_LENGTH
                        + " characters long");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Registration should throw Custom exception"
                        + " when login length is less than "
                        + MIN_LOGIN_LENGTH
                        + " characters"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_loginIsNull_notOk() {
        defaultUser.setLogin("");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Login can't be null, login must be at least "
                        + MIN_LOGIN_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_passwordIsNull_notOk() {
        defaultUser.setPassword("");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Password is empty, password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_passwordIsValid_ok() {
        defaultUser.setPassword("qwerty");
        registrationService.register(defaultUser);

        assertTrue(defaultUser.getPassword().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        User savedUser = storageDao.get(defaultUser.getLogin());
        assertEquals(defaultUser, savedUser, "User must be added to storage");
    }

    @Test
    void register_passwordIsShort_notOk() {
        defaultUser.setPassword("qwe");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_passwordEdgeCase_notOk() {
        defaultUser.setPassword("qwert");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_passwordEdgeCase_ok() {
        defaultUser.setPassword("qwertyui");

        assertTrue(defaultUser.getPassword().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        registrationService.register(defaultUser);
        assertNotNull(storageDao.get(defaultUser.getLogin()), "User must be added to storage");
    }

    @Test
    void register_passwordIsLong_ok() {
        assertTrue(defaultUser.getPassword().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        registrationService.register(defaultUser);
        assertNotNull(storageDao.get(defaultUser.getLogin()), "User must be added to storage");
    }

    @Test
    void register_ageIsNull_notOk() {
        defaultUser.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "User age is null, must be at least " + MIN_AGE);
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_ageIsLow_notOk() {
        defaultUser.setAge(15);

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "User age must be at list " + MIN_AGE + " y.o.");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void register_ageIsHigh_ok() {
        defaultUser.setAge(35);

        registrationService.register(defaultUser);
        assertNotNull(storageDao.get(defaultUser.getLogin()),
                "User must be added to storage");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
