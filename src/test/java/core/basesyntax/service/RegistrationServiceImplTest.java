package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private StorageDao storageDao;
    private User actualuser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        actualuser = new User();
        actualuser.setLogin("sidorov");
        actualuser.setPassword("123456");
        actualuser.setAge(35);
    }

    @Test
    void age_18_Ok() {
        actualuser.setAge(18);
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void age_more18_Ok() {
        actualuser.setAge(29);
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void age_less18_notOk() {
        actualuser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void age_null_notOk() {
        actualuser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void age_0_notOk() {
        actualuser.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void age_less0_notOk() {
        actualuser.setAge(-25);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void age_maxint_Ok() {
        actualuser.setAge(2147483647);
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void loginLength_6_Ok() {
        actualuser.setLogin("123456");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void loginLength_more6_Ok() {
        actualuser.setLogin("12345678009");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void loginLength_less6_notOk() {
        actualuser.setLogin("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void loginLength_0_notOk() {
        actualuser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void loginLength_null_notOk() {
        actualuser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void passwordLength_more6_Ok() {
        actualuser.setPassword("12345678009");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void passwordLength_less6_notOk() {
        actualuser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void passwordLength_6_notOk() {
        actualuser.setPassword("123456");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void passwordLength_null_notOk() {
        actualuser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualuser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
