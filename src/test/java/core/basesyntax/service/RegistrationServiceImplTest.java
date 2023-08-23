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
    private User testUser;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void initUser() {
        testUser = new User();
        testUser.setAge(23);
        testUser.setLogin("Login1");
        testUser.setPassword("123456");

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
            testUser.setPassword(null);
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setLogin(null);
            registrationService.register(testUser);
        });
    }

    @Test
    void register_minAge_OK() {
        testUser.setAge(18);
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_lowAge_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setAge(5);
            registrationService.register(testUser);
        });
    }

    @Test
    void register_minLoginAndPasswordLength_OK() {
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_shortLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setLogin("Login");
            registrationService.register(testUser);
        });
    }

    @Test
    void register_shortPassword_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setPassword("12345");
            registrationService.register(testUser);
        });
    }

    @Test
    void register_emptyLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setLogin("");
            registrationService.register(testUser);
        });
    }

    @Test
    void register_emptyPassword_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setPassword("");
            registrationService.register(testUser);
        });
    }

    @Test
    void register_registerUserWithSameLogin_notOK() {
        assertThrows(RegistrationException.class, () -> {
            testUser.setLogin(Storage.people.get(1).getLogin());
            registrationService.register(testUser);
        });
    }

    @Test
    void register_validCriteria_OK() {
        testUser.setAge(41);
        testUser.setLogin("BestUserEver");
        testUser.setPassword("qwerty1234ytrewq");
        assertEquals(testUser, registrationService.register(testUser));
    }
}
