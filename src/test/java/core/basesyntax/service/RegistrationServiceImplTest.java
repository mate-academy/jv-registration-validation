package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final String lessThan6Chars = "oops";
    private RegistrationService registrationService;
    private User testUser1;
    private User testUser2;
    private StorageDao storageDao;

    public User setUser() {
        User resultUser = new User();
        String over6Chars = "misterProper";
        resultUser.setLogin(over6Chars);
        resultUser.setPassword(over6Chars);
        resultUser.setAge(MIN_AGE);
        return resultUser;
    }

    @BeforeEach
    public void init() {
        testUser1 = setUser();
        testUser2 = setUser();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    public void teardown() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        }, "User cannot be null");
    }

    @Test
    public void register_nullLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setLogin(null);
            registrationService.register(testUser1);
        }, "Login cannot be null");
    }

    @Test
    public void register_shortLogin_notOk() {
        testUser1.setLogin(lessThan6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser1);
        }, "Login must be at least 6 characters");
    }

    @Test
    public void register_nullPassword_notOk() {
        testUser1.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser1);
        }, "Password cannot be null");
    }

    @Test
    public void register_shortPassword_notOk() {
        testUser1.setPassword(lessThan6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser1);
        }, "Password must be at least 6 characters");
    }

    @Test
    public void register_lessThanMinimalAge_notOk() {
        testUser1.setAge(9);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser1);
        }, "Min allowed age is " + MIN_AGE);
    }

    @Test
    public void register_moreThanMaximumAge_notOk() {
        testUser1.setAge(1000);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser1);
        }, "Max allowed age is " + MAX_AGE);
    }

    @Test
    public void register_loginIsTaken_notOk() {
        registrationService.register(testUser1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser2);
        }, " This login is already taken");
    }

    @Test
    public void register_successfulAdding_OK() {
        registrationService.register(testUser1);
        assertEquals(testUser1, storageDao.get(testUser1.getLogin()));
    }
}
