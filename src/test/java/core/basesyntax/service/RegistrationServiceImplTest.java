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
    private static RegistrationService registrationService;
    private User testUser1;
    private User testUser2;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void initUser() {
        testUser1 = new User();
        testUser1.setAge(23);
        testUser1.setLogin("Login1");
        testUser1.setPassword("123456");

        testUser2 = new User();
        testUser2.setAge(25);
        testUser2.setLogin("Login2");
        testUser2.setPassword("123458");

        Storage.people.clear();
        User user1 = new User();
        user1.setAge(21);
        user1.setLogin("UserUser");
        user1.setPassword("hdafbgv7");

        User user2 = new User();
        user2.setAge(81);
        user2.setLogin("LoginTest");
        user2.setPassword("23r5r5");

        User user3 = new User();
        user3.setAge(18);
        user3.setLogin("LoG1n123");
        user3.setPassword("9dfv76s");

        User user4 = new User();
        user4.setAge(33);
        user4.setLogin("BestUser");
        user4.setPassword("8q47rv654");
        Storage.people.add(user1);
        Storage.people.add(user2);
        Storage.people.add(user3);
        Storage.people.add(user4);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullPassword_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setPassword(null);
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_nullLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setLogin(null);
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_validAge_OK() {
        testUser1.setAge(18);
        assertEquals(testUser1, registrationService.register(testUser1));

        testUser2.setAge(31);
        assertEquals(testUser2, registrationService.register(testUser2));
    }

    @Test
    void register_lowAge_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setAge(17);
            registrationService.register(testUser1);
        });

        testUser2.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser2);
        });
    }

    @Test
    void register_validLogin_OK() {
        testUser1.setLogin("sixLet");
        assertEquals(testUser1, registrationService.register(testUser1));

        testUser2.setLogin("tenLetters");
        assertEquals(testUser2, registrationService.register(testUser2));
    }

    @Test
    void register_validPassword_OK() {
        testUser1.setPassword("123456");
        assertEquals(testUser1, registrationService.register(testUser1));

        testUser2.setPassword("12345678987654321");
        assertEquals(testUser2, registrationService.register(testUser2));
    }

    @Test
    void register_shortLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setLogin("Login");
            registrationService.register(testUser1);
        });

        testUser2.setLogin("Log");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser2);
        });
    }

    @Test
    void register_shortPassword_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setPassword("12345");
            registrationService.register(testUser1);
        });

        testUser2.setPassword("13");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser2);
        });
    }

    @Test
    void register_emptyLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setLogin("");
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_emptyPassword_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setPassword("");
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_registerUserWithSameLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser1.setLogin(Storage.people.get(1).getLogin());
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_validCriteria_OK() {
        testUser1.setAge(41);
        testUser1.setLogin("BestUserEver");
        testUser1.setPassword("qwerty1234ytrewq");
        assertEquals(testUser1, registrationService.register(testUser1));
    }
}
