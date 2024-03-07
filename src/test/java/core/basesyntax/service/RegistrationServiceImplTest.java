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
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Mendos");
        user.setPassword("123456");
        user.setAge(19);
    }

    @Test
    void register_ValidUser_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserWithNormalLengthLogin_Ok() {
        user.setLogin("Benjamin");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserWithNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithEmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithShortLogin_NotOk() {
        user.setLogin("2;3");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithEdgeShortLogin_NotOk() {
        user.setLogin("2;3qw");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNormalLengthPassword_Ok() {
        user.setLogin("12345678");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserWithEdgeLengthPassword_Ok() {
        user.setLogin("987654");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserWithNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithShortPassword_NotOk() {
        user.setPassword(".,7");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithEdgeShortPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithExistingLogin_NotOk() {
        User existingUser = new User();
        existingUser.setLogin("Mendos");
        existingUser.setPassword("9876543221");
        existingUser.setAge(30);
        registrationService.register(existingUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithYoungAge_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithNegativeAge_NotOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserWithEighteenAge_Ok() {
        user.setAge(18);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserWithOldAge_Ok() {
        user.setAge(90);
        assertEquals(user, registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
