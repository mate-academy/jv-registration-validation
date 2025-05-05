package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = defaultValidUser();
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        registerAndCheckUser(user);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeUnder18_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Age18_isOk() {
        user.setAge(18);
        registerAndCheckUser(user);
    }

    @Test
    void register_Age120_isOk() {
        user.setAge(120);
        registerAndCheckUser(user);
    }

    @Test
    void register_AgeMore120_notOk() {
        user.setAge(121);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = defaultValidUser();
        existingUser.setLogin("login123");
        Storage.people.add(existingUser);

        user.setLogin("login123");
        user.setAge(35);
        user.setPassword("new_password");

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_newUser_isOk() {
        user.setLogin("login123");
        registerAndCheckUser(user);

        User secondUser = defaultValidUser();
        secondUser.setAge(35);
        secondUser.setPassword("new_password");
        secondUser.setLogin("login1234");
        registerAndCheckUser(secondUser);
    }

    private void registerAndCheckUser(User user) {
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
        assertTrue(Storage.people.contains(user));
    }

    private User defaultValidUser() {
        User defaultUser = new User();
        defaultUser.setAge(20);
        defaultUser.setLogin("user_login");
        defaultUser.setPassword("password");
        return defaultUser;
    }
}
