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
    private static User user;

    @Test
    void emptyLines() throws RegistrationException {
        boolean expected = false;
        boolean emptyPass = user.getPassword() == null;
        boolean emptyAge = user.getAge() == null;
        boolean emptyLogin = user.getLogin() == null;
        assertEquals(expected, emptyPass, "Password is empty!");
        assertEquals(expected, emptyLogin, "Login is empty!");
        assertEquals(expected, emptyAge, "Age is empty!");
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User("", "", 0);
    }

    @Test
    void addTest_OK() throws RegistrationException {
        final int number = 1;
        user.setLogin("pokemon");
        user.setPassword("Rock99pop");
        user.setAge(23);

        registrationService.register(user);
        boolean expected = true;
        boolean actual = Storage.people.size() == number;
        assertEquals(expected, actual, "There must be one since one user has been added!");
    }

    @Test
    void registration_check_notOK() throws RegistrationException {
        user.setLogin("pokemon");
        user.setPassword("Rock99pop");
        user.setAge(23);
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void shortPassword_notOk() {
        user.setPassword("rock");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void minor_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UnvalidAge_notOK() {
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void getUser_OK() throws RegistrationException {
        User bob = new User("rockPop", "Toy78cook", 44);
        User johan = new User("stopCop", "Car098track", 56);
        User alice = new User("lotDog", "flay99board", 48);
        registrationService.register(bob);
        registrationService.register(johan);
        registrationService.register(alice);
        StorageDaoImpl dao = new StorageDaoImpl();
        dao.get("stopCop");
        assertEquals(johan, dao.get("stopCop"), "Wrong user search!");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
