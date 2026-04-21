package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;
    private User user;

    @BeforeEach
    public void setUp() {
        service = new RegistrationServiceImpl();
        user = new User();
        people.clear();
    }

    @Test
    public void register_userIsNull_notOk() {
        assertThrows(CustomException.class, () -> service.register(null));
    }

    @Test
    public void register_ageIsNull_notOk() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_loginIsNull_notOk() {
        user.setPassword("123456");
        user.setLogin(null);
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        user.setPassword(null);
        user.setLogin("Bob123");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_ageIsLessThenExpected_notOk() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(16);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_ageIsUnderThenExpected_ok() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(19);
    }

    @Test
    public void register_ageSameThenExpected_ok() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(18);
    }

    @Test
    public void register_ageIsNegativeNumber_notOk() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(-18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_zeroPasswordLength_notOk() {
        user.setPassword("");
        user.setLogin("Bob123");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_threePasswordLength_notOk() {
        user.setPassword("123");
        user.setLogin("Bob123");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_fivePasswordLength_notOk() {
        user.setPassword("12345");
        user.setLogin("Bob123");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_sixPasswordLength_ok() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(18);
    }

    @Test
    public void register_zeroLoginLength_notOk() {
        user.setPassword("123456");
        user.setLogin("");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_threeLoginLength_notOk() {
        user.setPassword("123456");
        user.setLogin("Bob");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_fiveLoginLength_notOk() {
        user.setPassword("123456");
        user.setLogin("Bob12");
        user.setAge(18);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_sixLoginLength_ok() {
        user.setPassword("123456");
        user.setLogin("Bob123");
        user.setAge(18);
    }

    @Test
    public void register_addUserInStorage_notOk() {
        user.setAge(18);
        user.setLogin("Bobby123");
        user.setPassword("123456");

        service.register(user);

        User user2 = new User();
        user2.setAge(18);
        user2.setLogin("Bobby123");
        user2.setPassword("123456");

        assertEquals(user, user2);
        assertThrows(CustomException.class, () -> service.register(user2));
    }

    @Test
    public void register_addUserInStorage_ok() {
        user.setAge(18);
        user.setLogin("Bobby123");
        user.setPassword("123456");

        service.register(user);

        User user2 = new User();
        user2.setAge(19);
        user2.setLogin("Scot123");
        user2.setPassword("123456");

        assertNotEquals(user, user2);

        User expected = user2;
        User actual = service.register(user2);
        assertEquals(expected, actual);
    }
}
