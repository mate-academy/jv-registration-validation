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
    private static RegistrationServiceImpl service;
    private static User user;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        user = new User();
        user1 = new User();
        user2 = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("123456");
        user.setPassword("123456");
        user.setAge(20);
        user1.setLogin("1234567");
        user1.setPassword("1234567");
        user1.setAge(21);
        user2.setLogin("12345678");
        user2.setPassword("12345678");
        user2.setAge(50);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_loginLess6Characters_notOk() {
        user.setLogin("test");
        user1.setLogin("t");
        user2.setLogin("12345");
        assertThrows(RegistrationException.class, () -> service.register(user));
        assertThrows(RegistrationException.class, () -> service.register(user1));
        assertThrows(RegistrationException.class, () -> service.register(user2));
    }

    @Test
    void register_passwordLess6Characters_notOk() {
        user.setPassword("test");
        user1.setPassword("N");
        user2.setPassword("999");
        assertThrows(RegistrationException.class, () -> service.register(user));
        assertThrows(RegistrationException.class, () -> service.register(user1));
        assertThrows(RegistrationException.class, () -> service.register(user2));
    }

    @Test
    void register_ageLess18_notOk() {
        user.setAge(15);
        user1.setAge(null);
        user2.setAge(-7);
        assertThrows(RegistrationException.class, () -> service.register(user));
        assertThrows(RegistrationException.class, () -> service.register(user1));
        assertThrows(RegistrationException.class, () -> service.register(user2));
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        User actual = service.register(user1);
        assertEquals(user1, actual);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        User actual = service.register(user2);
        assertEquals(user2, actual);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_sameLogin_notOk() {
        Storage.people.add(user);
        user1.setLogin(user.getLogin());
        assertThrows(RegistrationException.class, () -> service.register(user1));
    }

    @Test
    void register_addUser_ok() {
        User actual = service.register(user);
        User actual1 = service.register(user1);
        User actual2 = service.register(user2);
        assertEquals(user, actual);
        assertEquals(user1, actual1);
        assertEquals(user2, actual2);
    }
}
