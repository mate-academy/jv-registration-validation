package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    void userWithNoSuchLoginInStorage_Ok() {
        User testUser = new User();
        testUser.setLogin("login123");
        storageDao.add(testUser);
        assertNull(storageDao.get("login1234"));
    }

    @Test
    void userWithThisLoginInStorage_NotOk() {
        User testUser = new User();
        testUser.setLogin("login4321");
        storageDao.add(testUser);
        assertEquals(testUser, storageDao.get("login4321"));
    }

    @Test
    void loginHasAtLeastSixCharacters_Ok() {
        User testUser1 = new User();
        testUser1.setLogin("user12");
        testUser1.setPassword("password");
        testUser1.setAge(18);
        registrationService.register(testUser1);
        assertEquals(testUser1, storageDao.get("user12"));
        User testUser2 = new User();
        testUser2.setLogin("user123456");
        testUser2.setPassword("password");
        testUser2.setAge(18);
        registrationService.register(testUser2);
        assertEquals(testUser2, storageDao.get("user123456"));
        User testUser3 = new User();
        testUser3.setLogin("user123456!@#$");
        testUser3.setPassword("password");
        testUser3.setAge(18);
        registrationService.register(testUser3);
        assertEquals(testUser3, storageDao.get("user123456!@#$"));
    }

    @Test
    void loginIsShorterThanSixCharacters_NotOk() {
        User testUser1 = new User();
        testUser1.setLogin("login");
        testUser1.setPassword("password");
        testUser1.setAge(18);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser1));
        User testUser2 = new User();
        testUser2.setLogin("lo");
        testUser2.setPassword("password");
        testUser2.setAge(18);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser2));
        User testUser3 = new User();
        testUser3.setLogin("");
        testUser3.setPassword("password");
        testUser3.setAge(18);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser3));
    }

    @Test
    void passwordHasAtLeastSixCharacters_Ok() {
        User testUser = new User();
        testUser.setLogin("login1");
        testUser.setPassword("passwo");
        testUser.setAge(18);
        registrationService.register(testUser);
        assertEquals(testUser, storageDao.get("login1"));
        User testUser2 = new User();
        testUser2.setLogin("login2");
        testUser2.setPassword("password123");
        testUser2.setAge(18);
        registrationService.register(testUser2);
        assertEquals(testUser2, storageDao.get("login2"));
        User testUser3 = new User();
        testUser3.setLogin("login3");
        testUser3.setPassword("password123456!@#$%^&*()");
        testUser3.setAge(18);
        registrationService.register(testUser3);
        assertEquals(testUser3, storageDao.get("login3"));
    }

    @Test
    void passwordIsShorterThanSixCharacters_NotOk() {
        User testUser = new User();
        testUser.setLogin("login1");
        testUser.setAge(18);
        testUser.setPassword("pass1");
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
        User testUser2 = new User();
        testUser2.setLogin("login1");
        testUser2.setAge(18);
        testUser2.setPassword("pas");
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser2));
        User testUser3 = new User();
        testUser3.setLogin("login1");
        testUser3.setAge(18);
        testUser3.setPassword("");
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser3));
    }

    @Test
    void userAgeIsAtLeastEighteenYearsOld_Ok() {
        User testUser = new User();
        testUser.setLogin("testUser");
        testUser.setPassword("password");
        testUser.setAge(18);
        registrationService.register(testUser);
        assertEquals(testUser, storageDao.get("testUser"));
        User testUser2 = new User();
        testUser2.setLogin("testUser2");
        testUser2.setPassword("password");
        testUser2.setAge(28);
        registrationService.register(testUser2);
        assertEquals(testUser2, storageDao.get("testUser2"));
        User testUser3 = new User();
        testUser3.setLogin("testUser3");
        testUser3.setPassword("password");
        testUser3.setAge(123);
        registrationService.register(testUser3);
        assertEquals(testUser3, storageDao.get("testUser3"));
    }

    @Test
    void userAgeIsLessThanEighteenYearsOld_NotOk() {
        User testUser = new User();
        testUser.setLogin("login1");
        testUser.setPassword("password");
        testUser.setAge(17);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
        testUser.setAge(5);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void userAgeIsZero_NotOk() {
        User testUser = new User();
        testUser.setLogin("login1");
        testUser.setPassword("password");
        testUser.setAge(0);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void userAgeIsNegativeNumber_NotOk() {
        User testUser = new User();
        testUser.setLogin("login1");
        testUser.setPassword("password");
        testUser.setAge(-20);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void loginIsNull_NotOk() {
        User testUser = new User();
        testUser.setLogin(null);
        testUser.setPassword("password");
        testUser.setAge(17);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void passwordIsNull_NotOk() {
        User testUser = new User();
        testUser.setLogin("testUser");
        testUser.setPassword(null);
        testUser.setAge(17);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void ageIsNull_NotOk() {
        User testUser = new User();
        testUser.setLogin("testUser");
        testUser.setPassword("password");
        testUser.setAge(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void passwordLengthCheck_Ok() {
        User testUser = new User();
        testUser.setLogin("testUserPassword");
        testUser.setPassword("password");
        testUser.setAge(18);
        int passwordLengthExpected = 8;
        assertEquals(passwordLengthExpected,
                registrationService.register(testUser).getPassword().length());
    }

    @Test
    void loginLengthCheck_Ok() {
        User testUser = new User();
        testUser.setLogin("testUserLogin");
        testUser.setPassword("password");
        testUser.setAge(18);
        int loginLengthExpected = 13;
        assertEquals(loginLengthExpected,
                registrationService.register(testUser).getLogin().length());
    }

}
