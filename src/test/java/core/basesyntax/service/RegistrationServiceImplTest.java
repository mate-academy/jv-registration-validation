package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private User defaultUser;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        defaultUser = new User();
        defaultUser.setAge(90);
        defaultUser.setLogin("ksdsljkdflkasjd");
        defaultUser.setPassword("o3003030e00e");
    }

    @Test
    void register_nullArgument_notOk() {
        try {
            new RegistrationServiceImpl().register(null);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException expected for null argument");
    }

    @Test
    void register_loginIsNull_notOk() {
        try {
            defaultUser.setLogin(null);
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException expected for user.login = null");
    }

    @Test
    void register_ageIsNull_notOk() {
        try {
            defaultUser.setLogin("dfsd");
            defaultUser.setAge(null);
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException expected for user.age = null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        try {
            defaultUser.setPassword(null);
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException expected for user.password = null");
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        String usedLogin = "alksdlksdlk@lkksdlklf.llff";
        User storageUser = new User();
        storageUser.setLogin(usedLogin);
        storageDao.add(storageUser);
        try {
            defaultUser.setLogin(usedLogin);
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException, method allows user duplicates");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        try {
            defaultUser.setLogin("");
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException,"
                + "when login field empty an exception should be thrown");
    }

    @Test
    void register_default_Ok() {
        new RegistrationServiceImpl().register(defaultUser);
        assertEquals(storageDao.get(defaultUser.getLogin()).getLogin(), defaultUser.getLogin());
        assertEquals(storageDao.get(defaultUser.getLogin()).getPassword(),
                defaultUser.getPassword());
        assertEquals(storageDao.get(defaultUser.getLogin()).getAge(), defaultUser.getAge());
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        try {
            defaultUser.setLogin("eoooooppepep");
            defaultUser.setPassword("");
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_AgeLessThan18_notOk() {
        try {
            defaultUser.setLogin("osoooeoeoe");
            defaultUser.setAge(16);
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException, if age < 18 method you should thrown an exception");
    }

    @Test
    void register_AgeEquals18_Ok() {
        defaultUser.setAge(18);
        defaultUser.setLogin("dfghjkfghj");
        new RegistrationServiceImpl().register(defaultUser);
        assertEquals(storageDao.get(defaultUser.getLogin()).getAge(),
                defaultUser.getAge(), "Age not match");
    }

    @Test
    void register_AgeIsNegative_NotOk() {
        try {
            defaultUser.setLogin("dkdkkd");
            defaultUser.setAge(-23939);
            new RegistrationServiceImpl().register(defaultUser);
        } catch (InvalidRegistrationDataException e) {
            return;
        }
        fail("InvalidRegistrationDataException, if age < 0 method should thrown an exception");
    }

    @Test
    void register_AgeEquals999_Ok() {
        defaultUser.setLogin("d23232kdkkd");
        defaultUser.setAge(999);
        new RegistrationServiceImpl().register(defaultUser);
        assertEquals(storageDao.get("d23232kdkkd").getAge(), 999, "Age not match");
    }

}
