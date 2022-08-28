package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationServiceImpl registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("correctUser");
        testUser.setPassword("444444");
        testUser.setAge(18);
    }

    @Test
    void register_ValidUser_ok() {
        User actual = registrationService.register(testUser);
        assertEquals(testUser, actual);
        assertTrue(people.contains(testUser));
    }

    @Test
    void register_ExistingUser_notOk() {
        testUser.setLogin("newCorrectUser");
        registrationService.register(testUser);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_UserNullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_UserNullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_UserShortPassword_notOk() {
        User incorrectUserShortPassword = testUser;
        testUser.setPassword("444");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectUserShortPassword));
    }

    @Test
    void register_UserLessAge_notOk() {
        testUser.setAge(15);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_UserNullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_UserNegativeAge_notOk() {
        testUser.setAge(-15);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void check_userIsNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }
}
