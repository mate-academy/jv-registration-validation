package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationServiceImpl registrationService;
    private static User[] users;

    @BeforeAll
    static void beforeAll() {
        final int usersNumbs = 7;
        registrationService = new RegistrationServiceImpl();
        users = new User[usersNumbs];
        storageDao = new StorageDaoImpl();
        users[0] = new User("pokemon", "Rock99pop", 23);
        users[1] = new User("donald", "Tot77", 18);
        users[2] = new User("supra", "Lol556yo", 16);
        users[3] = new User("superCat", null, 23);
        users[4] = new User(null, "Toyota45cut", 23);
        users[5] = new User("logoBulls", "Toronto78punk", -53);
        users[6] = new User("", "", 23);
    }

    @Test
    void addTest_OK() throws RegistrationException {
        final int numbers = 1;
        registrationService.register(users[0]);
        boolean expected = true;
        boolean actual = Storage.people.size() == numbers;
        assertEquals(expected, actual, "There must be one since one user has been added!");
    }

    @Test
    void overwritingNotPossible_notOK() throws RegistrationException {
        registrationService.register(users[0]);
        assertThrows(RegistrationException.class, () -> registrationService.register(users[0]));
    }

    @Test
    void shortPassword_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(users[1]));
    }

    @Test
    void minor_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(users[2]));
    }

    @Test
    void nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(users[3]));
    }

    @Test
    void nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(users[4]));
    }

    @Test
    void ageMustBe18andNotMinus() {
        assertThrows(RegistrationException.class, () -> registrationService.register(users[5]));
    }

    @Test
    void emptyLines() {
        assertThrows(RegistrationException.class, () -> registrationService.register(users[6]));
    }

    @Test
    void getUser_OK() throws RegistrationException {
        User user001 = new User("rockPop", "Toy78cook", 44);
        User user002 = new User("stopCop", "Car098track", 56);
        User user003 = new User("lotDog", "flay99board", 48);
        registrationService.register(user001);
        registrationService.register(user002);
        registrationService.register(user003);
        StorageDaoImpl dao = new StorageDaoImpl();
        dao.get("stopCop");
        assertEquals(user002, dao.get("stopCop"), "Wrong user search!");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
