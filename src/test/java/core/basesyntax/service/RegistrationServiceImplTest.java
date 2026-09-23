package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.AgeValidationExeption;
import core.basesyntax.exeptions.LoginValidationException;
import core.basesyntax.exeptions.NullUserValidationExeption;
import core.basesyntax.exeptions.PasswordValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        User user1 = new User();
        user1.setLogin("testFirst");
        user1.setPassword("testPassword");
        user1.setAge(34);
        User user2 = new User();
        user2.setLogin("testSecond");
        user2.setPassword("testPassword");
        user2.setAge(35);
        User user3 = new User();
        user3.setLogin("testThird");
        user3.setPassword("testPassword");
        user3.setAge(36);
        User user4 = new User();
        user4.setLogin("testFourth");
        user4.setPassword("testPassword");
        user4.setAge(37);
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
        storageDao.add(user4);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testLV");
        user.setPassword("testPV");
        user.setAge(18);
    }

    @Test
    void register_userExistByLogin_NotOk() {
        user.setLogin("testFirst");
        assertThrows(LoginValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(NullUserValidationExeption.class, () -> registrationService.register(null));
    }

    @Test
    void register_LoginAtList6Characters_Ok() {
        user.setLogin("testForValidLogin");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual, "Test register_loginAtList6Characters_Ok failed");
    }

    @Test
    void register_Login6Characters_Ok() {
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual, "Test register_login6Characters_Ok failed");
    }

    @Test
    void register_LoginLessThen6Characters_NotOk() {
        user.setLogin("test");
        assertThrows(LoginValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginIsEmpty_NotOk() {
        user.setLogin("");
        assertThrows(LoginValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_notOk() {
        user.setLogin(null);
        assertThrows(LoginValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordAtList6Characters_Ok() {
        user.setLogin("testValidPassword");
        user.setPassword("testValidPassword");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual, "Test register_PasswordAtList6Characters_Ok failed");
    }

    @Test
    void register_Password6Characters_Ok() {
        user.setLogin("testPassword6Characters");
        user.setPassword("testPassword6Characters");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual, "Test register_Password6Characters_Ok failed");
    }

    @Test
    void register_PasswordLessThen6Characters_NotOk() {
        user.setLogin("testPasswordLessThen6Characters");
        user.setPassword("not");
        assertThrows(PasswordValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsEmpty_NotOk() {
        user.setLogin("testPasswordEmpty");
        user.setPassword("");
        assertThrows(PasswordValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setLogin("testNullPassword");
        user.setPassword(null);
        assertThrows(PasswordValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeMoreThan18_Ok() {
        user.setLogin("testAgeMoreThan18");
        user.setPassword("testAgeMoreThan18");
        user.setAge(25);
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual, "Test register_AgeMoreThan18_Ok failed");
    }

    @Test
    void register_Age18_Ok() {
        user.setLogin("testAge18");
        user.setPassword("testAge18");
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual, "Test register_Age18_Ok failed");
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        user.setLogin("testLessThanAge18");
        user.setPassword("testLessThanAge18");
        user.setAge(13);
        assertThrows(AgeValidationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_notOk() {
        user.setAge(null);
        assertThrows(AgeValidationExeption.class, () -> registrationService.register(user));
    }
}
