package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationValidationTest {
    private static final String VALID_LOGIN = "myLogin";
    private static final String VALID_PASSWORD = "myValidPassword";
    private static final String NOT_VALID_PASSWORD = "pass";
    private static final Integer VALID_AGE = 20;
    private static final Integer NOT_VALID_AGE = 16;
    private static StorageDao storageDao;
    private static RegistrationServiceImpl registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        testUser = new User();
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User testUser = null;

        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if user is null!");
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if login is null!");
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if password is null!");
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if age is null!");
    }

    @Test
    void register_validUserAdded_ok() {
        registrationService.register(testUser);
        assertEquals(testUser, storageDao.get(testUser.getLogin()),
                "Expected user exist at the storage!");
        assertNotEquals(null, testUser.getId(),
                "Expected user has id after adding to storage!");
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        Storage.people.add(testUser);
        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if login already exists!");
    }

    @Test
    void register_invalidPassword_notOk() {
        testUser.setPassword(NOT_VALID_PASSWORD);
        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if password length less then 6 characters!");
    }

    @Test
    void register_invalidAge_notOk() {
        testUser.setAge(NOT_VALID_AGE);
        assertThrows(RegistrationValidationException.class, () -> {
            registrationService.register(testUser);
        }, "Expected RegistrationValidationException if age less then 18!");
    }
}
