package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl rsi;
    private User actual = new User();

    @BeforeAll
    static void beforeAll() {
        User u = new User();
        u.setAge(20);
        u.setLogin("vasyasho");
        u.setPassword("123456");
        Storage.people.add(u);
        User u1 = new User();
        u1.setAge(21);
        u1.setLogin("vasyahuha");
        u1.setPassword("123456789");
        Storage.people.add(u1);
        rsi = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        actual.setPassword("123456");
        actual.setLogin("fffffffff");
        actual.setAge(18);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        actual.setLogin(null);
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        actual.setLogin("");
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        actual.setLogin("vasya");
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_nullAge_notOk() {
        actual.setAge(null);
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_ageLessThanNeed_notOk() {
        actual.setAge(10);
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        actual.setPassword(null);
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_passwordShort_notOk() {
        actual.setPassword("133");
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_loginJustContains_notOk() {
        actual.setLogin("vasyasho");
        assertThrows(InvalidUserException.class, () -> {
            rsi.register(actual);
        });
    }

    @Test
    void register_Ok() {
        actual.setLogin("vasyasho12");
        try {
            assertEquals(rsi.register(actual), actual);
        } catch (InvalidUserException e) {
            fail("Registration failed unexpectedly");
        }
    }
}
