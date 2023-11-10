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
    private static final int minAge = 18;
    private static final int maxAge = 100;
    private final String lessThan6Chars = "oops";
    private RegistrationService registrationService;
    private User testNullUser;
    private User testUser2;
    private StorageDao storageDao;

    @BeforeEach
    public void init() {
        testNullUser = new User();
        String over6Chars = "misterProper";
        testNullUser.setLogin(over6Chars);
        testNullUser.setPassword(over6Chars);
        testNullUser.setAge(minAge);
        testUser2 = new User();
        testUser2.setLogin(over6Chars);
        testUser2.setPassword(over6Chars);
        testUser2.setAge(minAge);
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
            testNullUser.setLogin(null);
            registrationService.register(testNullUser);
        }, "Login cannot be null");
    }

    @Test
    public void register_shortLogin_notOk() {
        testNullUser.setLogin(lessThan6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Login must be at least 6 characters");
    }

    @Test
    public void register_nullPassword_notOk() {
        testNullUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Password cannot be null");
    }

    @Test
    public void register_shortPassword_notOk() {
        testNullUser.setPassword(lessThan6Chars);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Password must be at least 6 characters");
    }

    @Test
    public void register_lessThanMinimalAge_notOk() {
        testNullUser.setAge(9);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Min allowed age is " + minAge);
    }

    @Test
    public void register_moreThanMaximumAge_notOk() {
        testNullUser.setAge(1000);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testNullUser);
        }, "Max allowed age is " + maxAge);
    }

    @Test
    public void register_loginIsTaken_notOk() {
        registrationService.register(testNullUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser2);
        }, " This login was already taken");
    }

    @Test
    public void register_successfulAdding_OK() {
        registrationService.register(testNullUser);
        assertEquals(testNullUser, storageDao.get(testNullUser.getLogin()));
    }
}
