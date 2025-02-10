package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final String VALID_LOGIN = "user12";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 18;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void userWithNoSuchLoginInStorage_Ok() {
        User testUser = new User("login123", VALID_PASSWORD, VALID_AGE);
        storageDao.add(testUser);
        assertNull(storageDao.get("login1234"));
    }

    @Test
    void userWithThisLoginInStorage_NotOk() {
        User testUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    void loginHasAtLeastSixCharacters_Ok() {
        User testUser1 = new User("user!!", VALID_PASSWORD, VALID_AGE);
        User testUser2 = new User("user1234!!", VALID_PASSWORD, VALID_AGE);
        User testUser3 = new User("user1234!!!@#$", VALID_PASSWORD, VALID_AGE);
        registrationService.register(testUser1);
        registrationService.register(testUser2);
        registrationService.register(testUser3);
        assertEquals(testUser1, storageDao.get("user!!"));
        assertEquals(testUser2, storageDao.get("user1234!!"));
        assertEquals(testUser3, storageDao.get("user1234!!!@#$"));
    }

    @Test
    void loginIsShorterThanSixCharacters_NotOk() {
        User testUser1 = new User("login", VALID_PASSWORD, VALID_AGE);
        User testUser2 = new User("lo", VALID_PASSWORD, VALID_AGE);
        User testUser3 = new User("", VALID_PASSWORD, VALID_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser1));
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser2));
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser3));
    }

    @Test
    void passwordHasAtLeastSixCharacters_Ok() {
        User testUser1 = new User("login1", "passwo", VALID_AGE);
        User testUser2 = new User("login2", "password123", VALID_AGE);
        User testUser3 = new User("login3", "password123456!@#$%^&*()", VALID_AGE);
        registrationService.register(testUser1);
        registrationService.register(testUser2);
        registrationService.register(testUser3);
        assertEquals(testUser1, storageDao.get("login1"));
        assertEquals(testUser2, storageDao.get("login2"));
        assertEquals(testUser3, storageDao.get("login3"));
    }

    @Test
    void passwordIsShorterThanSixCharacters_NotOk() {
        User testUser1 = new User(VALID_LOGIN, "pass1", VALID_AGE);
        User testUser2 = new User(VALID_LOGIN, "pas", VALID_AGE);
        User testUser3 = new User(VALID_LOGIN, "", VALID_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser1));
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser2));
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser3));
    }

    @Test
    void userAgeIsAtLeastEighteenYearsOld_Ok() {
        User testUser1 = new User("ageTestUser1", VALID_PASSWORD, 18);
        User testUser2 = new User("ageTestUser2", VALID_PASSWORD, 28);
        User testUser3 = new User("ageTestUser3", VALID_PASSWORD, 123);
        registrationService.register(testUser1);
        registrationService.register(testUser2);
        registrationService.register(testUser3);
        assertEquals(testUser1, storageDao.get("ageTestUser1"));
        assertEquals(testUser2, storageDao.get("ageTestUser2"));
        assertEquals(testUser3, storageDao.get("ageTestUser3"));
    }

    @Test
    void userAgeIsLessThanEighteenYearsOld_NotOk() {
        User testUser1 = new User(VALID_LOGIN, VALID_PASSWORD, 17);
        User testUser2 = new User(VALID_LOGIN, VALID_PASSWORD, 15);
        User testUser3 = new User(VALID_LOGIN, VALID_PASSWORD, 1);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser1));
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser2));
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser3));
    }

    @Test
    void userAgeIsZero_NotOk() {
        User testUser = new User(VALID_LOGIN, VALID_PASSWORD, 0);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void userAgeIsNegativeNumber_NotOk() {
        User testUser = new User(VALID_LOGIN, VALID_PASSWORD, -20);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void loginIsNull_NotOk() {
        User testUser = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void passwordIsNull_NotOk() {
        User testUser = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void ageIsNull_NotOk() {
        User testUser = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void passwordLengthCheck_Ok() {
        User testUser = new User(VALID_LOGIN + "passwordLenght", VALID_PASSWORD, VALID_AGE);
        int passwordLengthExpected = 8;
        assertEquals(passwordLengthExpected,
                registrationService.register(testUser).getPassword().length());
    }

    @Test
    void loginLengthCheck_Ok() {
        User testUser = new User(VALID_LOGIN + "loginLenght", VALID_PASSWORD, VALID_AGE);
        int loginLengthExpected = 17;
        assertEquals(loginLengthExpected,
                registrationService.register(testUser).getLogin().length());
    }
}
