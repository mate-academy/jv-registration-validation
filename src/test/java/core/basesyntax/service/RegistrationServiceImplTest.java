package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        testUser = new User();
        testUser.setPassword("valid_password");
        testUser.setLogin("valid_login");
        testUser.setAge(21);
    }

    @Test
    void register_nullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userWithNullAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWithNullLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWithNullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }


    @Test
    void register_userWithNullValues_NotOk() {
        testUser.setAge(null);
        testUser.setLogin(null);
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWithAgeLessThan18_NotOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageEqualIntMaxValue_Ok() {
        testUser.setAge(Integer.MAX_VALUE);
        assertDoesNotThrow(() -> registrationService.register(testUser));
    }

    @Test
    void register_userWithPasswordLessThanMinValue_NotOk() {
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWithValidValues_Ok() {
        User addedUser = registrationService.register(testUser);
        assertEquals(testUser, addedUser,
                "Method register must return added user.");
    }

    @Test
    void register_userIsAlreadyExist_NotOk() {
        registrationService.register(testUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }
}
