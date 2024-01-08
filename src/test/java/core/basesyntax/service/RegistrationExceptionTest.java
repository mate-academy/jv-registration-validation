package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationExceptionTest {
    private static RegistrationService registrationService;
    private User validUser;
    private User anotherValidUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        validUser = new User();
        validUser.setLogin("validUserLogin");
        validUser.setPassword("validUserPassword");
        validUser.setAge(21);
        anotherValidUser = new User();
        anotherValidUser.setLogin("anotherValidUserLogin");
        anotherValidUser.setPassword("anotherValidUserPassword");
        anotherValidUser.setAge(42);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUserLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_ExistedUserLogin_notOk() {
        Storage.people.add(validUser);
        anotherValidUser.setLogin(validUser.getLogin());
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(anotherValidUser);
        });
    }

    @Test
    void register_UserLoginLength_notOk() {
        String[] notOkLogins = new String[] {"", "a", "abc", "abcde"};
        for (String login : notOkLogins) {
            validUser.setLogin(login);
            assertThrows(RegistrationException.class, () -> {
                registrationService.register(validUser);
            });
        }
    }

    @Test
    void register_UserLogin_Ok() {
        String[] okLogins = new String[] {"abcdef", "abcdefg", "abcdefgh"};
        for (String login : okLogins) {
            validUser.setLogin(login);
            registrationService.register(validUser);
            Storage.people.clear();
        }
    }

    @Test
    void register_nullUserPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_UserPasswordLength_notOk() {
        String[] notOkPasswords = new String[] {"", "a", "abc", "abcde"};
        for (String password : notOkPasswords) {
            validUser.setPassword(password);
            assertThrows(RegistrationException.class, () -> {
                registrationService.register(validUser);
            });
        }
    }

    @Test
    void register_UserPassword_Ok() {
        String[] okPasswords = new String[] {"abcdef", "abcdefg", "abcdefgh"};
        for (String password : okPasswords) {
            validUser.setPassword(password);
            registrationService.register(validUser);
            Storage.people.clear();
        }
    }

    @Test
    void register_nullUserAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_UserAgeValue_notOk() {
        int[] notOkAges = new int[] {-67, -19, -18, -17, -1, 0, 1, 17};
        for (int age : notOkAges) {
            validUser.setAge(age);
            assertThrows(RegistrationException.class, () -> {
                registrationService.register(validUser);
            });
            Storage.people.clear();
        }
    }

    @Test
    void register_UserAgeValue_Ok() {
        int[] okAges = new int[] {18, 19, 67, 109};
        for (int age : okAges) {
            validUser.setAge(age);
            registrationService.register(validUser);
            Storage.people.clear();
        }
    }
}
