package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final String DEFAULT_LOGIN = "TestLogin";
    private static final String DEFAULT_PASS = "123456";
    private static final Integer DEFAULT_AGE = 18;
    private User defaultUser;

    @BeforeEach
    public void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASS);
        defaultUser.setAge(DEFAULT_AGE);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_NullLogin_NotOk() {
        User actual = defaultUser;
        actual.setLogin(null);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_NullPass_NotOk() {
        User actual = defaultUser;
        actual.setPassword(null);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_NullAge_NotOk() {
        User actual = defaultUser;
        actual.setAge(null);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_SameLogin_NotOk() {
        User actual = new User();
        actual.setLogin("BobMarly");
        actual.setPassword("123456");
        actual.setAge(25);
        storageDao.add(actual);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_InvalidLogin_NotOk() {
        User actual = defaultUser;
        actual.setLogin("abc");
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
        actual.setLogin("");
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
        actual.setLogin("abcvd");
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_InvalidPass_NotOk() {
        User actual = defaultUser;
        actual.setPassword("csa");
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
        actual.setPassword("");
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
        actual.setPassword("abc*a");
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_InvalidAge_NotOk() {
        User actual = defaultUser;
        actual.setAge(0);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
        actual.setAge(10);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
        actual.setAge(17);
        assertThrows(InvalidDataRegistrationExeption.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_ValidUser_Ok() {
        User actual = new User();
        actual.setLogin("Rak_Stanis");
        actual.setPassword("1234563");
        actual.setAge(22);
        User expected = new User();
        expected.setLogin("Rak_Stanis");
        expected.setPassword("1234563");
        expected.setAge(22);
        assertEquals(expected, registrationService.register(actual));
    }
}
