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
    private User actualUser;
    private User actualUser2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        actualUser = new User();
        actualUser.setLogin("bober1");
        actualUser.setPassword("teeth123");
        actualUser.setAge(30);
        actualUser2 = new User();
        actualUser2.setLogin("uzhik1");
        actualUser2.setPassword("1tail1");
        actualUser2.setAge(20);
    }

    @Test
    void register_loginLengthAboveSix_ok() {
        actualUser.setLogin("bober12");
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_loginLengthEqualsSix_ok() {
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_loginLengthLessThanSix_notOk() {
        actualUser.setLogin("bober");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
        actualUser.setLogin("");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_nullLogin_notOk() {
        actualUser.setLogin(null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_passwordLengthAboveSix_ok() {
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_passwordLengthEqualsSix_ok() {
        actualUser.setPassword("teeth1");
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        actualUser.setPassword("teeth");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
        actualUser.setLogin("");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_nullPassword_notOk() {
        actualUser.setPassword(null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_ageAbove18_ok() {
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_ageEquals18_ok() {
        actualUser.setAge(18);
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_ageBelow18_notOk() {
        actualUser.setAge(16);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
        actualUser.setAge(0);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_nullAge_notOk() {
        actualUser.setAge(null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_negativeAge_notOk() {
        actualUser.setAge(-30);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
    }

    @Test
    void register_maxIntAge_ok() {
        actualUser.setAge(Integer.MAX_VALUE);
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        storageDao.add(actualUser);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser));
        actualUser2.setLogin("bober1");
        storageDao.add(actualUser2);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(actualUser2));
    }

    @Test
    void register_getRequestedUser_ok() {
        User expectedUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser);
        assertEquals(Storage.people.size(), 1);

        expectedUser = registrationService.register(actualUser2);
        assertEquals(expectedUser, actualUser2);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualUser2);
        assertEquals(Storage.people.size(), 2);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
