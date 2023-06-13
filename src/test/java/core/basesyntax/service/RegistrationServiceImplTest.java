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
    void register_ReturnValidUser_Ok() {
        registrationService.register(defaultUser);

        User result = storageDao.get(defaultUser.getLogin());
        assertNotNull(storageDao.get(defaultUser.getLogin()), "User should be added to storage");
        assertEquals(defaultUser, result, "Returned user should be the same as user added");
    }

    @Test
    void checkLogin_LoginUnique_Ok() {
        assertNull(storageDao.get(defaultUser.getLogin()), "Login must be unique");
        Storage.people.add(defaultUser);
        User result = storageDao.get(defaultUser.getLogin());
        assertNotNull(storageDao.get(defaultUser.getLogin()));
        assertEquals(defaultUser, result,
                "User saved to storage must be the same as User was created");
    }

    @Test
    void checkLogin_loginExists_NotOk() {
        User uniqueUser = defaultUser;
        Storage.people.add(uniqueUser);

        User notUniqueUser = defaultUser;
        assertNotNull(storageDao.get(uniqueUser.getLogin()),
                "This user must be present in storage");
        assertEquals(uniqueUser, notUniqueUser,
                "Can't register user, this login already in use");
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(notUniqueUser),
                "Registration should throw Custom Exception when login already exists");
    }

    @Test
    void checkLogin_loginLength_Ok() {
        User user = defaultUser;
        Storage.people.add(user);
        User result = storageDao.get(user.getLogin());

        assertTrue(user.getLogin().length() >= MIN_LOGIN_LENGTH,
                "Login must be at least "
                        + MIN_LOGIN_LENGTH
                        + " characters long"
        );
        assertEquals(user, result, "User login must be saved to storage");
    }

    @Test
    void checkLogin_loginLengthIsShort_NotOk() {
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
    void checkLogin_loginIsNull_NotOk() {
        defaultUser.setLogin("");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Login can't be null, login must be at least "
                        + MIN_LOGIN_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void checkPassword_passwordIsNull_NotOk() {
        defaultUser.setPassword("");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Password is empty, password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void checkPassword_passwordIsValid_Ok() {
        registrationService.register(defaultUser);

        assertTrue(defaultUser.getLogin().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        User result = storageDao.get(defaultUser.getLogin());
        assertEquals(defaultUser, result, "User must be added to storage");
    }

    @Test
    void checkPassword_passwordIsShort_NotOk() {
        defaultUser.setPassword("qwe");

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void checkPassword_passwordIsLong_Ok() {
        assertTrue(defaultUser.getLogin().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at least "
                        + MIN_PASSWORD_LENGTH
                        + " characters long"
        );
        registrationService.register(defaultUser);
        assertNotNull(storageDao.get(defaultUser.getLogin()), "User must be added to storage");
    }

    @Test
    void checkAge_ageIsNull_NotOk() {
        defaultUser.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "User age is null, must be at least " + MIN_AGE);
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void checkAge_ageIsLow_NotOk() {
        defaultUser.setAge(15);

        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "User age must be at list " + MIN_AGE + " y.o.");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void checkAge_ageIsHigh_Ok() {
        assertTrue(defaultUser.getAge() >= MIN_AGE,
                "User age must be at list " + MIN_AGE + " y.o.");
        registrationService.register(defaultUser);
        assertNotNull(storageDao.get(defaultUser.getLogin()),
                "User must be added to storage");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
