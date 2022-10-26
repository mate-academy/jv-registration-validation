package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "nobodyReadsIt";
    private static final String DEFAULT_PASSWORD = "itsTo123";
    private static final String NON_VALID_PASSWORD = "Mate";
    private static final String EMPTY_FIELD = "";
    private static final int DEFAULT_AGE = 27;
    private static final int NON_VALID_AGE = 16;
    private static RegistrationServiceImpl testService;
    private User testUser;

    @BeforeAll
    static void start() {
        testService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setAge(DEFAULT_AGE);
        testUser.setLogin(DEFAULT_LOGIN);
        testUser.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void cleanStarage() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_NotOk() {
        testUser = null;
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NullLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NullAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_ShortPassword_NotOk() {
        testUser.setPassword(NON_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_UserExist_NotOk() {
        Storage.people.add(testUser);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_ToSmallAge_NotOk() {
        testUser.setAge(NON_VALID_AGE);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        testUser.setLogin(EMPTY_FIELD);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NormalUserAdding_Ok() {
        assertEquals(testUser, testService.register(testUser));
    }
}
