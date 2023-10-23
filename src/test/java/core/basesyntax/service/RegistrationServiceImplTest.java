package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private User testUser1 = new User();
    private User testUser2 = new User();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        testUser1 = new User("anton1", "password", 20);
        testUser2 = new User("anton1", "another password", 19);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_Valid_Data_Ok() {
        registrationService.register(testUser1);
        assertEquals(testUser1, storageDao.get(testUser1.getLogin()));
    }

    @Test
    void register_Invalid_Age_NotOk() {
        testUser1.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Age_Is_Null_NotOk() {
        testUser1.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Invalid_Login_NotOk() {
        testUser1.setLogin("5char");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_LoginWithSpacesOnly_NotOk() {
        testUser1.setLogin("      ");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_LoginWithOneSpace_NotOk() {
        testUser1.setLogin("an ton");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Login_Is_Null_NotOk() {
        testUser1.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Invalid_Password_NotOk() {
        testUser1.setPassword("5char");
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Password_Is_Null_NotOk() {
        testUser1.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_User_Is_Null_NotOk() {
        testUser1 = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_User_Login_Exists_NotOk() {
        registrationService.register(testUser1);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser2));
    }
}
