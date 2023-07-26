package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.AlreadyRegisteredException;
import core.basesyntax.exceptions.ValidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INITIAL_TEST_AGE = 18;
    private static final int TEST_YOUNG_AGE = 5;
    private static final String INITIAL_TEST_LOGIN = "Stray228";
    private static final String TEST_SHORT_LOGIN = "Cat";
    private static final String TEST_WRONG_LOGIN = "C%)( t";
    private static final String INITIAL_TEST_PASSWORD = "123456";
    private static final String TEST_SHORT_PASSWORD = "1234";
    private static final String TEST_WRONG_PASSWORD = "$@*T@G %@*)%)&H";
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin(INITIAL_TEST_LOGIN);
        testUser.setPassword(INITIAL_TEST_PASSWORD);
        testUser.setAge(INITIAL_TEST_AGE);
    }

    @Test
    void register_user_OK() {
        assertDoesNotThrow(() -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_shortPassword_notOK() {
        testUser.setPassword(TEST_SHORT_PASSWORD);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOK() {
        testUser.setPassword(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_shortLogin_notOK() {
        testUser.setLogin(TEST_SHORT_LOGIN);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOK() {
        testUser.setLogin(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAlreadyRegistered_notOK() {
        Storage.people.add(testUser);
        assertThrows(AlreadyRegisteredException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_youngAge_notOK() {
        testUser.setAge(TEST_YOUNG_AGE);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOK() {
        testUser.setAge(null);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAddedToStorage_OK() {
        assertDoesNotThrow(() -> {
            registrationService.register(testUser);
        });
        User actual = storageDao.get(testUser.getLogin());
        User expected = testUser;
        assertEquals(expected,actual);
    }

    @Test
    void register_wrongPassword_notOK() {
        testUser.setPassword(TEST_WRONG_PASSWORD);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_wrongLogin_notOK() {
        testUser.setLogin(TEST_WRONG_LOGIN);
        assertThrows(ValidDataException.class, () -> registrationService.register(testUser));
    }

    @AfterEach
    void onTearDown() {
        Storage.people.clear();
    }
}
