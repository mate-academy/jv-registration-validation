package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String SHORT_PASSWORD = "12345";
    private static final String MIN_PASSWORD = "123456";
    private static final String DEFAULT_USER_LOGIN = "Marcus";
    private static final String DEFAULT_PASSWORD = "Marcus4ever";
    private static final int DEFAULT_AGE = 20;
    private static final int MIN_AGE = 18;
    private static RegistrationServiceImpl registrationService;
    private User testUser1;
    private User testUser2;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser1 = new User(DEFAULT_USER_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        testUser2 = new User(DEFAULT_USER_LOGIN, MIN_PASSWORD,DEFAULT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        testUser1.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_ageLessThanMinAge_notOk() {
        testUser1.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        testUser1.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_passwordToShort_notOk() {
        testUser1.setPassword(SHORT_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        registrationService.register(testUser1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser2);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        testUser1.setAge(-19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_userWithUniqueLogin_ok() {
        registrationService.register(testUser1);
        testUser2.setLogin("Unique Name");
        registrationService.register(testUser2);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_minPassword_ok() {
        testUser1.setPassword(MIN_PASSWORD);
        registrationService.register(testUser1);
        assertNotNull(testUser1.getId());
        assertNotNull(testUser1.getPassword());
        assertTrue(Storage.people.contains(testUser1));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_userMinAge_ok() {
        testUser1.setAge(MIN_AGE);
        registrationService.register(testUser1);
        assertNotNull(testUser1.getId());
        assertNotNull(testUser1.getAge());
        assertTrue(Storage.people.contains(testUser1));
        assertEquals(1, Storage.people.size());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
