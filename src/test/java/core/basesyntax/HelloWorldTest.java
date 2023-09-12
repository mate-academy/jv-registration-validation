package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void storageClear() {
        Storage.PEOPLE.clear();
    }

    @Test
    void test_UserNull_NotOK() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void test_loginIsNull_NotOk() {
        User user = new User(null, "qwerty1234", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_loginIsEmptyLine_NotOk() {
        User user = new User("", "qwerty1234", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_loginMinLength_NotOk() {
        User user = new User("werqq", "qwerty1234", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_loginMinLength_Ok() {
        User expected = new User("qwertywerwer", "qwerty1234", 21);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void test_loginAlreadyExist_NotOk() {
        User user = new User("qwerty12", "qwerty1234", 21);
        Storage.PEOPLE.add(user);
        User user1 = new User("qwerty12", "asdfghjk", 22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
    }

    @Test
    void test_passwordIsNull_NotOk() {
        User user = new User("qwertywerwer", null, 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_passwordMinLength_Ok() {
        User expected = new User("qwertywerwer", "qwertyy", 21);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void test_passwordEmptyLine_NotOk() {
        User user = new User("qwertywerwer", "", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_passwordMinLength_NotOk() {
        User user = new User("qwertywerwer", "qwety", 21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_AgeLessThenMinAge_NotOk() {
        User user = new User("qwertywerwer", "qweqwer", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_AgeMoreThenMinAge_Ok() {
        User expected = new User("qwertywerwer", "qweqwer", 18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }
}
