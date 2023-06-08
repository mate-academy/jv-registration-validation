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

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registeredUser_ReturnValidUser_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(33);
        Storage.people.add(user);

        User result = storageDao.get(user.getLogin());
        assertNotNull(storageDao.get(user.getLogin()), "User should be added to storage");
        assertEquals(user, result, "Returned user should be the same as user added");
    }

    @Test
    void userLogin_IsUnique_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(33);
        assertNull(storageDao.get(user.getLogin()), "Login must be unique");
        Storage.people.add(user);
        User result = storageDao.get(user.getLogin());
        assertNotNull(storageDao.get(user.getLogin()));
        assertEquals(user, result, "User saved to storage must be the same as User was created");
    }

    @Test
    void userLogin_AlreadyExists_NotOk() {
        User uniqueUser = new User();
        uniqueUser.setLogin("testlogin");
        uniqueUser.setPassword("password");
        uniqueUser.setAge(33);
        Storage.people.add(uniqueUser);

        User notUniqueUser = new User();
        notUniqueUser.setLogin("testlogin");
        notUniqueUser.setPassword("password");
        notUniqueUser.setAge(33);
        assertNotNull(storageDao.get(uniqueUser.getLogin()),
                "This user must be present in storage");
        assertEquals(uniqueUser, notUniqueUser,
                "Can't register user, this login already in use");
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(notUniqueUser),
                "Registration should throw Custom Exception when login already exists");
    }

    @Test
    void loginLength_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(33);
        Storage.people.add(user);
        User result = storageDao.get(user.getLogin());

        assertTrue(user.getLogin().length() >= MIN_LOGIN_LENGTH,
                "Login must be at list 6 characters long");
        assertEquals(user, result, "User login must be saved to storage");
    }

    @Test
    void loginLength_IsShort_NotOk() {
        User user = new User();
        user.setLogin("art");
        user.setPassword("password");
        user.setAge(33);

        assertFalse(user.getLogin().length() >= MIN_LOGIN_LENGTH,
                "Login must be at list 6 characters long");

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration should throw Custom exception"
                        + " when login length is less than 6 characters");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void login_IsNull_NotOk() {
        User user = new User();
        user.setPassword("password");
        user.setAge(33);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login can't be null, login must be at list 6 characters long");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void password_IsNull_NotOk() {
        User user = new User();
        user.setLogin("testlogin");
        user.setAge(33);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password is empty, password must be at list 6 characters long");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void password_IsValid_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(33);
        registrationService.register(user);

        assertTrue(user.getLogin().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at list 6 characters long");
        User result = storageDao.get(user.getLogin());
        assertEquals(user, result, "User must be added to storage");
    }

    @Test
    void password_IsShort_NotOk() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("pass");
        user.setAge(33);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password must be at list 6 characters long");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void password_IsLong_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("passwordislong");
        user.setAge(33);

        assertTrue(user.getLogin().length() >= MIN_PASSWORD_LENGTH,
                "Password must be at list 6 characters long");
        registrationService.register(user);
        assertNotNull(storageDao.get(user.getLogin()), "User must be added to storage");
    }

    @Test
    void age_IsNull_NotOk() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User age is null, must be minimum 18");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void age_IsUnderRequirements_NotOk() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User age must be at list 18 y.o.");
        assertTrue(Storage.people.isEmpty(), "Storage must be empty");
    }

    @Test
    void age_IsHigh_Ok() {
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("password");
        user.setAge(33);

        assertTrue(user.getAge() >= MIN_AGE, "Age must be not less than 18 y.o.");
        registrationService.register(user);
        assertNotNull(storageDao.get(user.getLogin()), "User must be added to storage");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
