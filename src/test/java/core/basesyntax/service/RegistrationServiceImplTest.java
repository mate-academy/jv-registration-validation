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

    public static final String LOGIN_INITIAL = "ksdsljkdflkasjd";
    public static final String PASSWORD_INITIAL = "o3003030e00e";
    public static final String LOGIN_FIRST = "dfsd";
    public static final String LOGIN_SECOND = "alksdlksdlk@lkksdlklf.llff";
    public static final String LOGIN_THIRD = "eoooooppepep";
    public static final String LOGIN_FOURTH = "osoooeoeoe";
    public static final String LOGIN_FIFTH = "dfghjkfghj";
    public static final String LOGIN_SIXTH = "dkdkkd";
    public static final String LOGIN_SEVENTH = "d23232kdkkd";
    public static final int AGE_FIRST = 16;
    public static final int AGE_SECOND = 18;
    public static final int AGE_THIRD = -23939;
    public static final int AGE_FOURTH = 999;
    public static final int AGE_INITIAL = 90;
    private User defaultUser;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        defaultUser = new User();
        defaultUser.setAge(AGE_INITIAL);
        defaultUser.setLogin(LOGIN_INITIAL);
        defaultUser.setPassword(PASSWORD_INITIAL);
    }

    @Test
    void register_nullArgument_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            new RegistrationServiceImpl().register(null);
        }, "InvalidRegistrationDataException expected for null argument");
    }

    @Test
    void register_loginIsNull_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(null);
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.login = null");
    }

    @Test
    void register_ageIsNull_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(LOGIN_FIRST);
            defaultUser.setAge(null);
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.age = null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setPassword(null);
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException expected for user.password = null");
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        User storageUser = new User();
        storageUser.setLogin(LOGIN_SECOND);
        storageDao.add(storageUser);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(LOGIN_SECOND);
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException, method allows user duplicates");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin("");
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException,"
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
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(LOGIN_THIRD);
            defaultUser.setPassword("");
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_AgeLessThan18_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(LOGIN_FOURTH);
            defaultUser.setAge(AGE_FIRST);
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException, if age < 18 method you should thrown an exception");
    }

    @Test
    void register_AgeEquals18_Ok() {
        defaultUser.setAge(AGE_SECOND);
        defaultUser.setLogin(LOGIN_FIFTH);
        new RegistrationServiceImpl().register(defaultUser);
        assertEquals(storageDao.get(defaultUser.getLogin()).getAge(),
                defaultUser.getAge(), "Age not match");
    }

    @Test
    void register_AgeIsNegative_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            defaultUser.setLogin(LOGIN_SIXTH);
            defaultUser.setAge(AGE_THIRD);
            new RegistrationServiceImpl().register(defaultUser);
        }, "InvalidRegistrationDataException, if age < 0 method should thrown an exception");
    }

    @Test
    void register_AgeEquals999_Ok() {
        defaultUser.setLogin(LOGIN_SEVENTH);
        defaultUser.setAge(AGE_FOURTH);
        new RegistrationServiceImpl().register(defaultUser);
        assertEquals(storageDao.get(LOGIN_SEVENTH).getAge(), AGE_FOURTH, "Age not match");
    }

}
