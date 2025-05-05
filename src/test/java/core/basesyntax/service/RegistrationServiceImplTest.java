package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeAll
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void registerWith_correctUserData_ok() {
        Storage.people.clear();
        User user = getValidUser();
        User actualUser1 = registrationService.register(user);
        assertEquals(user, actualUser1);

    }

    @Test
    void registerWith_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void registerWith_nullUserLogin_notOk() {
        User user = getValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_emptyUserLogin_notOk() {
        User user = getValidUser();
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_shortUserLogin_notOk() {
        User user = getValidUser();
        user.setLogin("Max");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_nullUserPassword_notOk() {
        User user1 = getValidUser();
        User user2 = getValidUser();
        user1.setPassword(null);
        user2.setPassword("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void registerWith_shortUserPassword_notOk() {
        User user = getValidUser();
        user.setPassword("0q4");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_tooOldUserAge_notOk() {
        User user = getValidUser();
        user.setAge(125);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_tooYoungUserAge_notOk() {
        User user = getValidUser();
        user.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "The user must be at least 18 years of age. Registration is not possible");
    }

    @Test
    void registerWith_negativeUserAge_notOk() {
        User user = getValidUser();
        user.setAge(-22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You can not set the user a negative age value. Registration is not possible");
    }

    @Test
    void registerWith_nullUserAge_notOk() {
        User user = getValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWith_containedInDbUser_notOk() {
        User user = getValidUser();
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    private User getValidUser() {
        User user = new User();
        user.setLogin("Anhelina");
        user.setPassword("0q9w8r7t6y");
        user.setAge(35);
        return user;
    }
}
