package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NotValidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User nullUser;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        nullUser = null;
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_userIsUnder18_notOk() {
        user.setAge(17);
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userIsOver18_isOk() {
        user.setAge(18);
        Integer actual = user.getAge();
        assertTrue(actual >= 18);
    }

    @Test
    void register_returnsUser_isOk() {
        user.setAge(18);
        user.setId(12345L);
        user.setPassword("123456");
        user.setLogin("login6");
        assertEquals(User.class,registrationService.register(user).getClass());
    }

    @Test
    void register_loginLength_notOk() {
        user.setAge(18);
        user.setId(12345L);
        user.setPassword("123456");
        user.setLogin("login");
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLength_notOk() {
        user.setAge(18);
        user.setId(12345L);
        user.setPassword("12345");
        user.setLogin("login6");
        assertThrows(NotValidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_storageWasIncreased_Ok() {
        user.setAge(18);
        user.setId(12345L);
        user.setPassword("123456");
        user.setLogin("login6");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }
}
