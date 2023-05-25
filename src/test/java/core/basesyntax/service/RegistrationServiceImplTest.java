package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        Storage.people.clear();
    }

    @Test
    void register_OneUser_IsOk() {
        user.setAge(18);
        user.setLogin("login1");
        user.setPassword("123456");
        service.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_LargeDataInput_IsOk() {
        user.setAge(99);
        user.setPassword("1112284623846822");
        user.setLogin("longExampleLogin_qwertyuiopasdfghjkl");
        service.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_NullDataInput_NotOk() {
        user.setLogin(null);
        user.setAge(20);
        user.setPassword("123456");
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setLogin("login1");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setAge(null);
        user.setPassword("654321");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_UnderMinValue_NotOk() {
        user.setAge(6);
        user.setPassword("123456");
        user.setLogin("login1");
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setAge(20);
        user.setPassword("1");
        assertThrows(RegistrationException.class, () -> service.register(user));
        user.setPassword("123456");
        user.setLogin("1");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_sameLogin_NotOk() {
        user.setAge(22);
        user.setPassword("123654");
        user.setLogin("same_login");
        service.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
        user = new User();
        user.setAge(21);
        user.setPassword("333666");
        user.setLogin("same_login");
        assertThrows(RegistrationException.class, () -> service.register(user));
        assertEquals(expected, actual);
    }

    @Test
    void register_FiveUsers_isOk() {
        user.setAge(22);
        user.setPassword("123654");
        user.setLogin("same_login");
        service.register(user);
        user = new User();
        user.setAge(56);
        user.setPassword("1234579796");
        user.setLogin("login1");
        service.register(user);
        user = new User();
        user.setAge(25);
        user.setPassword("123645354");
        user.setLogin("login_example");
        service.register(user);
        user = new User();
        user.setAge(29);
        user.setPassword("123786654");
        user.setLogin("same_login111222n");
        service.register(user);
        user = new User();
        user.setAge(72);
        user.setPassword("12987983654");
        user.setLogin("smile_login");
        service.register(user);
        int expected = 5;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_NullUser_Not_Ok() {
        user = null;
        assertThrows(RegistrationException.class, () -> service.register(user));
    }
}
