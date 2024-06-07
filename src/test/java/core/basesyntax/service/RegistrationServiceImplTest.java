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
    private User actualUser;
    private User nextActualUser;
    private StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        actualUser = new User();
        actualUser.setLogin("sidorov");
        actualUser.setPassword("123456");
        actualUser.setAge(35);
        nextActualUser = new User();
        nextActualUser.setLogin("Ivan Ivanov");
        nextActualUser.setPassword("123456asd");
        nextActualUser.setAge(25);
    }

    @Test
    void register_17Age_notOk() {
        actualUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_nullAge_notOk() {
        actualUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_18Age_Ok() {
        actualUser.setAge(18);
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_maxIntAge_Ok() {
        actualUser.setAge(2147483647);
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_negativeAge_notOk() {
        actualUser.setAge(-25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_zeroAge_notOk() {
        actualUser.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_passwordNull_notOk() {
        actualUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        actualUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
        actualUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_passwordLengthEqualSix_Ok() {
        actualUser.setPassword("123456");
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_passwordLengthMoreThanSix_Ok() {
        actualUser.setPassword("123456789");
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_loginNull_notOk() {
        actualUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_loginLengthLessThanSix_notOk() {
        actualUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
        actualUser.setLogin("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_loginLengthEqualSix_Ok() {
        actualUser.setLogin("123456");
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_loginLengthMoreThanSix_Ok() {
        actualUser.setLogin("123456789");
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_existLogin_notOk() {
        storageDao.add(actualUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualUser));
        storageDao.add(nextActualUser);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nextActualUser));
    }

    @Test
    void register_rightUser_Ok() {
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);

        expectedUser = registrationService.register(nextActualUser);
        assertEquals(expectedUser, nextActualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), nextActualUser);
        assertEquals(Storage.people.size(), 2);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
