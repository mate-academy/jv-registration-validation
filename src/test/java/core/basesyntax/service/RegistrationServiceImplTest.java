package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 25;
    private static final String VALID_LOGIN = "Alice";
    private static final String VALID_PASSWORD = "1234567";
    private static final int AGE_LESS_18 = 17;
    private static final int AGE_NEGATIVE = -20;
    private static final String PASSWORD_LESS_SIX_SYMBOLS = "12345";
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_NullUser_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_SameLogin_NotOk() {
        String sameLogin = VALID_LOGIN;
        final User actual = new User();
        user.setLogin(sameLogin);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_PasswordLessSixSymbol_NotOk() {
        user.setPassword(PASSWORD_LESS_SIX_SYMBOLS);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ValidData_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_AgeLess18_NotOk() {
        user.setAge(AGE_LESS_18);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NegativeAge_NotOk() {
        user.setAge(AGE_NEGATIVE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
