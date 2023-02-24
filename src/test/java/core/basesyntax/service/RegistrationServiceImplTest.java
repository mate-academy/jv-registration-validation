package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User defaultUser;
    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        defaultUser = new User();
        defaultUser.setAge(90);
        defaultUser.setLogin("initial_login");
        defaultUser.setPassword("initial_password");
    }

    @Test
    void register_nullArgument_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        }, "InvalidRegistrationDataException expected for null argument");
    }

    @Test
    void register_loginIsNull_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(null);
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.login = null");
    }

    @Test
    void register_ageIsNull_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("first_login");
            defaultUser.setAge(null);
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.age = null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setPassword(null);
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.password = null");
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        User storageUser = new User();
        storageUser.setLogin("second_login");
        storageDao.add(storageUser);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("second_login");
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, method allows user duplicates");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("");
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException,"
                + "when login field empty an exception should be thrown");
    }

    @Test
    void register_default_ok() {
        registrationService.register(defaultUser);
        assertEquals(storageDao.get(defaultUser.getLogin()).getLogin(), defaultUser.getLogin());
        assertEquals(storageDao.get(defaultUser.getLogin()).getPassword(),
                defaultUser.getPassword());
        assertEquals(storageDao.get(defaultUser.getLogin()).getAge(), defaultUser.getAge());
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("third_login");
            defaultUser.setPassword("");
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_AgeLessThan18_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("fourth_login");
            defaultUser.setAge(16);
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, if age < 18 method you should thrown an exception");
    }

    @Test
    void register_AgeEquals18_ok() {
        defaultUser.setAge(18);
        defaultUser.setLogin("login_fifth");
        registrationService.register(defaultUser);
        assertEquals(storageDao.get(defaultUser.getLogin()).getAge(),
                defaultUser.getAge(), "Age not match");
    }

    @Test
    void register_AgeIsNegative_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("sixth_login");
            defaultUser.setAge(-23939);
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, if age < 0 method should thrown an exception");
    }

    @Test
    void register_AgeEquals999_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("login_seventh");
            defaultUser.setAge(999);
            registrationService.register(defaultUser);
        }, "InvalidRegistrationDataException, if age > 140 method should thrown an exception");
    }

}
