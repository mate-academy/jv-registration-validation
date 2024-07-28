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
    void register_validUser_Ok() {
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(actualuser);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_ageEighteen_Ok() {
        actualuser.setAge(18);
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_moreEighteenAge_Ok() {
        actualuser.setAge(29);
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void age_lessEighteenAge_notOk() {
        actualuser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void register_nullAge_notOk() {
        actualuser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void register_zeroAge_notOk() {
        actualuser.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void register_lessZeroAge_notOk() {
        actualuser.setAge(-25);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void register_maxintAge_Ok() {
        actualuser.setAge(2147483647);
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void regitser_loginLengthSix_Ok() {
        actualuser.setLogin("123456");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void regitser_loginLengthMoreSix_Ok() {
        actualuser.setLogin("12345678009");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void regitser_loginLengthLessSix_notOk() {
        actualuser.setLogin("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void regitser_loginLength0_notOk() {
        actualuser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void regitser_loginNull_notOk() {
        actualuser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void register_passwordLengthMoreSix_Ok() {
        actualuser.setPassword("12345678009");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void regitser_passwordLengthLessSix_notOk() {
        actualuser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @Test
    void regitser_passwordLengthSix_Ok() {
        actualuser.setPassword("123456");
        User expectedUser = registrationService.register(actualuser);
        assertEquals(expectedUser, actualuser);
        assertEquals(storageDao.get(expectedUser.getLogin()), actualuser);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void regitser_passwrodNull_notOk() {
        actualuser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(actualuser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
