package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = createUser("qwerty", "123456", 18);

        User actual = registrationService.register(user);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals("qwerty", actual.getLogin());
        Assertions.assertEquals("123456", actual.getPassword());
        Assertions.assertEquals(18, actual.getAge());
        Assertions.assertEquals(1, Storage.people.size());
        Assertions.assertEquals(actual, Storage.people.get(0));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, "123456", 18);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser("qwerty", null, 18);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createUser("qwerty", "123456", null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthLessThan6_notOk() {
        User user = createUser("abc", "123456", 18);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        User user = createUser("qwerty", "123", 18);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_age17_notOk() {
        User user = createUser("qwerty", "123456", 17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createUser("qwerty", "123456", -1);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        // IMPORTANT: add user directly to storage, not via register() (checklist advice)
        User existing = createUser("qwerty", "123456", 20);
        Storage.people.add(existing);

        User newUserWithSameLogin = createUser("qwerty", "abcdef", 25);

        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUserWithSameLogin));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
