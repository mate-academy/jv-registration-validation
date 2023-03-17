package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String EXCEPTION_MASSAGE = RegistrationService.class.toString();
    private static final String VALID_LOGIN = "Acxbbmnb";
    private static final String VALID_PASSWORD = "123456";
    private static int VALID_USER_AGE = 18;
    private static Long VALID_ID = Long.valueOf(12334);
    private static RegistrationService registrationService;
    private static StorageDao storage;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @Test
    void register_nullLogin_NotOk() {
        User userTest = new User(VALID_ID, null, VALID_PASSWORD, VALID_USER_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_shortPassword_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, "123", VALID_USER_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_nullAge_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_underAge_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, VALID_PASSWORD, 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_nullPassword_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, null, VALID_USER_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_minusAge_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, VALID_PASSWORD, -20);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_exist_NotOk() {
        User user = new User(VALID_ID, VALID_LOGIN, "895759",45);
        storage.add(user);
        User user2 = new User(VALID_ID,VALID_LOGIN, "356789", 18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_Ok() {
        User user = new User(VALID_ID,VALID_LOGIN,VALID_PASSWORD,VALID_USER_AGE);
        User register = registrationService.register(user);
        assertSame(storage.get(VALID_LOGIN), register);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}

