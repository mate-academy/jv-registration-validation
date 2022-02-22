package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService register;
    private static User defaultUser;

    @BeforeAll
    static void beforeAll() {
        register = new RegistrationServiceImpl();
    }

    @BeforeEach
    void validUserInitialization() {
        defaultUser = new User();
        defaultUser.setAge(19);
        defaultUser.setLogin("first");
        defaultUser.setPassword("1234567890");
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
        defaultUser = null;
    }

    @Test
    void register_ValidUser_Ok() {
        try {
            register.register(defaultUser);
        } catch (RuntimeException e) {
            fail("Failed registration");
        }
    }

    @Test
    void register_NullUser_NotOk() {
        defaultUser = null;
        assertThrows(RuntimeException.class, () -> register.register(defaultUser));
    }

    @Test
    void register_LowerAge_NotOk() {
        defaultUser.setAge(17);
        try {
            register.register(defaultUser);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    void register_UserNullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> register.register(defaultUser));
    }

    @Test
    void register_UserAge18_Ok() {
        defaultUser.setAge(18);
        assertNotNull(register.register(defaultUser));
    }

    @Test
    void register_UserNegativeAge_NotOk() {
        defaultUser.setAge(-2);
        assertThrows(RuntimeException.class, () -> register.register(defaultUser));
    }

    @Test
    void register_UserWithSameLogin_NotOk() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("first");
        user1.setId(1L);
        user1.setPassword("123456789");
        User user2 = new User();
        user2.setAge(20);
        user2.setLogin("second");
        user2.setId(2L);
        user2.setPassword("987654321");
        register.register(user1);
        register.register(user2);
        assertThrows(RuntimeException.class, () -> register.register(defaultUser));
    }

    @Test
    void register_UserWithDifferentLogin_Ok() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("third");
        user1.setId(1L);
        user1.setPassword("123456789");
        User user2 = new User();
        user2.setAge(20);
        user2.setLogin("second");
        user2.setId(2L);
        user2.setPassword("987654321");
        register.register(user1);
        register.register(user2);
        assertNotNull(register.register(defaultUser));
    }

    @Test
    void register_UserNullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class,() -> register.register(defaultUser));
    }

    @Test
    void register_userNullPassword_NotOk() {
        defaultUser.setPassword(null);
        try {
            register.register(defaultUser);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    void register_UserShortPassword_notOk() {
        defaultUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> register.register(defaultUser));
    }

    @Test
    void register_UserPasswordLength6_Ok() {
        defaultUser.setPassword("123456");
        assertNotNull(register.register(defaultUser));
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
