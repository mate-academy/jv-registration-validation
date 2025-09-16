package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void register_loginNull_notOk() {
        User user = new User(null, "111111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginNotEmpty_notOk() {
        User user = new User(" ", "111111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginLength0_notOk() {
        User user = new User("", "111111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginLength3_notOk() {
        User user = new User("222", "111111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginLength5_notOk() {
        User user = new User("22222", "111111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginLength6_Ok() {
        User user = new User("222222", "111111", 43);
        User actual = service.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_loginLengthGreater6_Ok() {
        User user = new User("111111111111111", "222222", 43);
        User actual = service.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User("222222", null, 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLength0_notOk() {
        User user = new User("222222", "", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordNotEmpty_notOk() {
        User user = new User("222222", " ", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLength3_notOk() {
        User user = new User("222222", "111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLength5_notOk() {
        User user = new User("222222", "11111", 34);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLength6_Ok() {
        User user = new User("222222", "111111", 43);
        User actual = service.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_passwordLengthGreater6_Ok() {
        User user = new User("222222", "1111111111111", 43);
        User actual = service.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_userAgeAdmissible_Ok() {
        User user = new User("222222", "111111", 18);
        User actual = service.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_userAge17_notOk() {
        User user = new User("222222", "111111", 17);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User("222222", "111111", null);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userAgeZero_notOk() {
        User user = new User("222222", "111111", 0);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userAgeNegative_notOk() {
        User user = new User("222222", "111111", -1);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_duplicateLoginUser_notOk() {
        StorageDao dao = new StorageDaoImpl();
        User existingUser = new User("222222", "111111", 21);
        Storage.people.add(existingUser);
        User userWithSameLogin = dao.get("222222");
        assertThrows(RegistrationException.class, () -> {
            service.register(userWithSameLogin);
        });
    }
}
