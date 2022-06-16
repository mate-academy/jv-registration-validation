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
    private static RegistrationServiceImpl registrationService;
    private static User john;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        john = new User();
    }

    @BeforeEach
    void setUp() {
        john.setAge(19);
        john.setLogin("John12.03.1993");
        john.setPassword("12345678");
    }

    @Test
    void validUser_Ok() {
        registrationService.register(john);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void userWithLessAge_Ok() {
        john.setAge(17);
        int actual = Storage.people.size();
        registrationService.register(john);
        int expected = Storage.people.size();
        assertEquals(actual, expected);
    }

    @Test
    void addUserWithSameLogin_Ok() {
        registrationService.register(john);
        User vanya = new User();
        vanya.setAge(24);
        vanya.setLogin("John12.03.1993");
        vanya.setPassword("dfsndlfwe19383");
        int expected = Storage.people.size();
        registrationService.register(vanya);
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void lessThanSixSymbolsPassword_Ok() {
        john.setPassword("123");
        int expected = Storage.people.size();
        registrationService.register(john);
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void nullLoginUser_NotOk() {
        john.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void nullPasswordUser_NotOk() {
        john.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void nullAgeUser_NotOk() {
        john.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void nullUser_NotOk() {
        john = null;
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(john);
        });
        john = new User();
    }

    @AfterEach
    void tearDown() {
        for (int i = 0; i < Storage.people.size(); i++) {
            Storage.people.remove(i);
        }
    }
}
