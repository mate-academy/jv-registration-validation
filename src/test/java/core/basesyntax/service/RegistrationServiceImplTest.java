package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
    void password_atLeastSixSymbols_ok() {
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(new User("login124","ValidP",21));
        });
    }

    @Test
    void login_atLeastSixSymbols_ok() {
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(new User("validL","123456754",25));
        });
    }

    @Test
    void age_Over18_ok() {
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(new User("validLogin","123456",18));
        });
    }

    @Test
    void age_Under18_notOk() {
        Assertions.assertThrows(RegistrationException.class,() -> {
            registrationService.register(new User("validLog","ValidPassword",17));
        });
    }

    @Test
    void login_twoUsersWithTheSameLogin_notOk() {
        User firstUser = new User("sameLogin","1234567",21);
        User secondUser = new User("sameLogin","1239876",45);
        Storage.people.add(firstUser);

        RegistrationException actual = Assertions.assertThrows(RegistrationException.class,() -> {
            registrationService.register(secondUser);
        });
        Assertions.assertEquals("User is already added to Storage", actual.getMessage());
    }

    @Test
    void password_shortPassword_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("ValidLogin","short",18));
        });
    }

    @Test
    void login_shortLogin_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("short","ValidPassword",18));
        });
    }

    @Test
    void login_addUsersWithDifferentLogin_ok() {
        User firstUser = new User("first_Login","1234567",21);
        User secondUser = new User("second_Login","1239876",45);
        User user3 = new User("Login_user3","123Valid",49);
        Assertions.assertDoesNotThrow(() -> {
            registrationService.register(firstUser);
            registrationService.register(secondUser);
            registrationService.register(user3);
        });
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User());
        });
    }

    @Test
    void storage_addUser_ok() {
        User firstUser = new User("first_Login","1234567",21);
        Assertions.assertTrue(Storage.people.add(firstUser));
        Assertions.assertTrue(Storage.people.contains(firstUser));
    }

    @Test
    void register_returnCorrectUser_ok() {
        User expected = new User("correct_Login","1234567",21);
        User actual = registrationService.register(expected);
        Assertions.assertEquals(expected,actual);
    }
}
