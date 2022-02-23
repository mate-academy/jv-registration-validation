package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static RegistrationService registrationService;
    private static User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
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
            registrationService.register(defaultUser);
        } catch (RuntimeException e) {
            fail("Failed registration");
        }
        User userFromStorage = Storage.people.get(0);
        assertEquals(defaultUser, userFromStorage);
        assertEquals(defaultUser.getPassword(), userFromStorage.getPassword());
        assertEquals(defaultUser.getAge(), userFromStorage.getAge());
        assertEquals(defaultUser.getLogin(), userFromStorage.getLogin());
    }

    @Test
    void register_NullUser_NotOk() {
        defaultUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_LowerAge_NotOk() {
        defaultUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_UserNullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_UserAge18_Ok() {
        defaultUser.setAge(18);
        assertNotNull(registrationService.register(defaultUser));
    }

    @Test
    void register_UserNegativeAge_NotOk() {
        defaultUser.setAge(-2);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
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
        registrationService.register(user1);
        registrationService.register(user2);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
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
        registrationService.register(user1);
        registrationService.register(user2);
        assertNotNull(registrationService.register(defaultUser));
    }

    @Test
    void register_UserNullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void register_userNullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_UserShortPassword_notOk() {
        defaultUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_UserPasswordLength6_Ok() {
        defaultUser.setPassword("123456");
        assertNotNull(registrationService.register(defaultUser));
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
