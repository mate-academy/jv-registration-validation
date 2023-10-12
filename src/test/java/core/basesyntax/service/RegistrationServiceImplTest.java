package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final int ELEMENTS = 1;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User correctUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser = new User();
        correctUser.setAge(20);
        correctUser.setLogin("david23");
        correctUser.setPassword("12ab34cd");

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullValue_notOk() {
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(null);
        }, "If user is null it should throw BadDataValidation Exception!");
    }

    @Test
    void register_user_isOk() {
        registrationService.register(correctUser);
        assertEquals(correctUser, storageDao.get(correctUser.getLogin()));
    }

    @Test
    void register_storageSize_isOk() {
        registrationService.register(correctUser);
        int expected = ELEMENTS;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_userLogin_notOk() {
        correctUser.setLogin("12345");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user login is not valid it should throw BadDataValidation Exception!");
        correctUser.setLogin("123 45");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user login is not valid it should throw BadDataValidation Exception!");
        correctUser.setLogin("12");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user login is not valid it should throw BadDataValidation Exception!");
        correctUser.setLogin("");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user login is not valid it should throw BadDataValidation Exception!");
    }

    @Test
    void register_loginIsNull_notOk() {
        correctUser.setLogin(null);
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user login is Null it should throw BadDataValidation Exception!");
    }

    @Test
    void register_password_notOk() {
        correctUser.setPassword("1234 5Aa bc");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user password is not valid it should throw BadDataValidation Exception!");
        correctUser.setPassword("12as3");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user password is not valid it should throw BadDataValidation Exception!");
        correctUser.setPassword("");
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user password is not valid it should throw BadDataValidation Exception!");
    }

    @Test
    void register_passwordIsNull_notOk() {
        correctUser.setPassword(null);
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user password Null it should throw BadDataValidation Exception!");
    }


    @Test
    void register_userAge_notOk() {
        correctUser.setAge(17);
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user age is not valid it should throw BadDataValidation Exception!");
    }

    @Test
    void register_userAgeIsNull_notOk() {
        correctUser.setAge(null);
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If user age is Null it should throw BadDataValidation Exception!");
    }

    @Test
    void register_loginDuplicate_notOk() {
        storageDao.add(correctUser);
        assertThrows(BadDataValidationException.class, () -> {
            registrationService.register(correctUser);
        }, "If login is duplicated it should throw BadDataValidation Exception!");
    }
}