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
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login");
        user.setPassword("veryLong-C00l_password");
        user.setAge(18);
    }

    @Test
    void register_validUserData_Ok() {
        User actual = registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_existingLogin_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_youngerMinYO_notOk() {
        int minLimitedAge = RegistrationServiceImpl.MINIMUM_USERS_AGE;
        user.setAge(minLimitedAge - 1);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_passShorterMin_notOk() {
        int minPasswordChars = RegistrationServiceImpl.MINIMUM_PASS_CHARS;
        String badPassword = user.getPassword().substring(0, minPasswordChars - 1);
        user.setPassword(badPassword);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_nullUser_notOk() {
        user = new User();
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_nullPass_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "You should throw your custom unchecked exception in case of invalid data");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
