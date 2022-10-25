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
    private static RegistrationServiceImpl testService;
    private User testUser;

    @BeforeAll
    static void start() {
        testService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setAge(27);
        testUser.setLogin("nobodyReadsIt");
        testUser.setPassword("itsTo123");
    }

    @AfterEach
    void cleanStarage() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NormalUserAdding_Ok() {
        assertEquals(testUser, testService.register(testUser));
    }

    @Test
    void register_NullUser_NotOk() {
        testUser = null;
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_UserExist_NotOk() {
        Storage.people.add(testUser);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_ToSmallAge_NotOk() {
        testUser.setAge(16);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        testUser.setLogin("");
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NullLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> testService.register(testUser));
    }

    @Test
    void register_NullAge_NotOk() {
        assertThrows(RuntimeException.class, () -> testService.register(null));
    }

    @Test
    void register_ShortPassword_NotOk() {
        testUser.setPassword("Mate");
        assertThrows(RuntimeException.class, () -> testService.register(null));
    }
}
