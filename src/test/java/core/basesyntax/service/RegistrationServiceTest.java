package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("InstantiationOfUtilityClass")
class RegistrationServiceTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final Integer DEFAULT_AGE = 20;
    private static User DEFAULT_USER;
    RegistrationService service;

    @BeforeEach
    void setUp() {
        DEFAULT_USER = new User( DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void CheckedRegister_Ok() {
        User actual = service.register(DEFAULT_USER);
        assertEquals(DEFAULT_USER, actual);
    }

    @Test
    void CheckedAge_NotOk() {
        DEFAULT_USER.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
        DEFAULT_USER.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
        DEFAULT_USER.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
        DEFAULT_USER.setAge(5000);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
            System.out.println(Storage.people.size());
        });
    }

    @Test
    void CheckedUserIsExist_NotOk() {
        service.register(DEFAULT_USER);
        assertThrows(RuntimeException.class, () -> {
            service.register(DEFAULT_USER);
        });
    }

    @Test
    void PasswordValid_NotOk() {
        DEFAULT_USER.setPassword("4aaaa");
        assertThrows(RuntimeException.class, () -> {
           service.register(DEFAULT_USER);
        });
    }

    @Test
    void Register_1000_Users_Ok() {
        for (int i = 1; i <= 1000; i++) {
            User user = new User(DEFAULT_LOGIN + i, DEFAULT_PASSWORD, DEFAULT_AGE);
            service.register(user);
        }
        assertEquals(1000, Storage.people.size());
    }
}
