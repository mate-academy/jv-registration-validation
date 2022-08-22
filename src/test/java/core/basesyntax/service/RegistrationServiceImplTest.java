package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User newUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin("Henry");
        user1.setPassword("3452345");
        user1.setAge(19);
        registrationService.register(user1);
    }

    @BeforeEach
    void setUp() {
        newUser = new User();
    }

    @Test
    void register_nullLogin_notOk() {
        newUser.setPassword("123456");
        newUser.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        newUser.setLogin("Tony");
        newUser.setAge(19);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullAge_notOk() {
        newUser.setLogin("Tony");
        newUser.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_passwordLengthLessThenMin_notOk() {
        newUser.setLogin("Tony");
        newUser.setPassword("12345");
        newUser.setAge(18);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageLessThenMin_notOk() {
        newUser.setLogin("Tony");
        newUser.setPassword("123456");
        newUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithAlreadyExistingLogin_notOk() {
        newUser.setLogin("Henry");
        newUser.setPassword("123456");
        newUser.setAge(22);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageValueIsNegative_notOk() {
        newUser.setLogin("John");
        newUser.setPassword("123456");
        newUser.setAge(-42);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userRegister_Ok() {
        newUser.setLogin("Bob");
        newUser.setPassword("123456");
        newUser.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(newUser));
    }
}
