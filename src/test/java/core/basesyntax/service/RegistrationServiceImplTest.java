package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void userDuplicate_notOk() {
        User duplicate = registrationService.register(new User("Alex", "qwerty", 18));
        assertThrows(RuntimeException.class, () -> registrationService.register(duplicate));
    }

    @Test
    void userRegistration_Ok() {
        User expected = storageDao.get("Alex");
        assertNotNull(expected);
        System.out.println("User added to List");
        User current = new User("Bob", "123456", 20);
        assertEquals(current,registrationService.register(current));
        System.out.println("method return current user");
    }

    @Test
    void nullUser_notOk(){
        actual = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void nullUserLogin_notOk() {
        actual = new User(null, "qwerty", 18);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void nullUserAge_notOk() {
        actual = new User("Cris", "qwerty", null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void nullUserPass_notOk() {
        actual = new User("Dan", null, 18);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }


    @Test
    void userAge_notOk() {
        actual = new User("Eric", "qwerty", 15);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void userPassword_notOk() {
        actual = new User("Felix", "12345", 18);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }
}