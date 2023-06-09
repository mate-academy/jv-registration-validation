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
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_Age_0_notOk() {
        user.setAge(0);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-10);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Age_10_notOk() {
        user.setAge(10);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Age_18_Ok() {
        user.setAge(18);
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_Age_20_Ok() {
        user.setAge(20);
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortLogin_5_notOk() {
        user.setLogin("login");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Login_6_Ok() {
        user.setLogin("logins");
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_Login_8_Ok() {
        user.setLogin("newlogin");
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortPassword_4_notOk() {
        user.setPassword("word");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortPassword_5_notOk() {
        user.setPassword("super");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Password_6_Ok() {
        user.setPassword("static");
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_Password_7_Ok() {
        user.setPassword("publick");
        Storage.people.add(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user));
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
