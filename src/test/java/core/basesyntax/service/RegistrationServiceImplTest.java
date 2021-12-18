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

    @BeforeAll
    static void initRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void addUsers() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setAge(25);
        user1.setPassword("qwertyui");
        User user2 = new User();
        user2.setLogin("user2");
        user2.setAge(66);
        user2.setPassword("asdfghj");
        User user3 = new User();
        user3.setLogin("user3");
        user3.setAge(35);
        user3.setPassword("zxcvbnm");
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(65);
        user.setPassword("123989789234");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_notExistUser_Ok() {
        User user = new User();
        user.setAge(65);
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_existUser_notOk() {
        User user = new User();
        user.setAge(65);
        user.setPassword("123989789234");
        user.setLogin("user1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User();
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIs18_Ok() {
        User user = new User();
        user.setAge(18);
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_ageIsMoreThan18_Ok() {
        User user = new User();
        user.setAge(19);
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_ageIsLessThan18_NotOk() {
        User user = new User();
        user.setAge(17);
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNegative_notOk() {
        User user = new User();
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        user.setAge(-98);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsZero_notOk() {
        User user = new User();
        user.setPassword("123989789234");
        user.setLogin("TestUser");
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIs6Characters_Ok() {
        User user = new User();
        user.setAge(22);
        user.setPassword("123456");
        user.setLogin("TestUser");
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_passwordLessThen6Chars_notOk() {
        User user = new User();
        user.setAge(61);
        user.setPassword("123");
        user.setLogin("TestUser");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsEmptyString_notOk() {
        User user = new User();
        user.setAge(61);
        user.setPassword("");
        user.setLogin("TestUser");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setAge(61);
        user.setLogin("TestUser");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

}
