package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("myLogin", "myPassword", 21);
    }

    @Test
    void register_User_Ok() {
        registrationService.register(user);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIs0_notOk() {
        user.setAge(0);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void ageIsNegative_notOk() {
        user.setAge(-10);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void shortLogin_notOk() {
        user.setLogin("login");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void shortPassword_notOk() {
        user.setPassword("word");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void passwordIsEmpty_notOk() {
        user.setPassword("");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void getAge_Ok() {
        int expected = 21;
        int actual = registrationService.register(user).getAge();
        assertEquals(expected, actual);
    }

    @Test
    void setAge_Ok() {
        user.setAge(44);
        int expected = 44;
        int actual = registrationService.register(user).getAge();
        assertEquals(expected, actual);
    }

    @Test
    void getLogin_Ok() {
        String expected = "myLogin";
        String actual = registrationService.register(user).getLogin();
        assertEquals(expected, actual);
    }

    @Test
    void setLogin_Ok() {
        user.setLogin("myName");
        String expected = "myName";
        String actual = registrationService.register(user).getLogin();
        assertEquals(expected, actual);
    }

    @Test
    void getPassword_Ok() {
        String expected = "myPassword";
        String actual = registrationService.register(user).getPassword();
        assertEquals(expected, actual);
    }

    @Test
    void setPassword_Ok() {
        user.setPassword("myNewPassword");
        String expected = "myNewPassword";
        String actual = registrationService.register(user).getPassword();
        assertEquals(expected, actual);
    }

    @Test
    void userInStorage_Ok() {
        Storage.people.add(user);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
