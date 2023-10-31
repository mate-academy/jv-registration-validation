package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterAll
    public static void deleteAllUserAfterTest() {
        Storage.people.clear();
    }

    @BeforeEach
    public void deleteAllUser() {
        Storage.people.clear();
    }

    @Test
    public void register_addUser_ok() {
        List<User> users = getListNormalUser();
        for (User user : users) {
            User actual = registrationService.register(user);
            assertEquals(actual, user);
        }
        assertEquals(users, Storage.people);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(null));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = getNormalUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = getNormalUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = getNormalUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User user = getNormalUser();
        user.setLogin("");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setLogin("123");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_invalidPassword_notOk() {
        User user = getNormalUser();
        user.setPassword("");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setPassword("123");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_invalidAge_notOk() {
        User user = getNormalUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setAge(-17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_addSameUser_notOk() {
        User user = getNormalUser();
        registrationService.register(user);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setAge(25);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        user.setPassword("1234567890");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    private User getNormalUser() {
        User user = new User();
        user.setPassword("123456");
        user.setLogin("123456");
        user.setAge(18);
        return user;
    }

    private List<User> getListNormalUser() {
        List<User> users = new ArrayList<>();
        User user1 = getNormalUser();
        users.add(user1);
        User user2 = new User();
        user2.setAge(25);
        user2.setLogin("12345678");
        user2.setPassword("12345678");
        users.add(user2);
        User user3 = new User();
        user3.setAge(100000000);
        user3.setLogin("234u23u");
        user3.setPassword("^09s(&*%#$");
        users.add(user3);
        return users;
    }
}
