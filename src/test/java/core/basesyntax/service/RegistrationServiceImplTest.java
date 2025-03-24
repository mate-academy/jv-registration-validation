package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_valid_ok() {
        User validUser = new User();
        validUser.setLogin("validUser");
        validUser.setPassword("strongPassword");
        validUser.setAge(23);

        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    void register_existingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("firstUser");
        firstUser.setPassword("password");
        firstUser.setAge(20);
        Storage.people.add(firstUser);

        User secondUser = new User();
        secondUser.setLogin("firstUser");
        secondUser.setPassword("password2");
        secondUser.setAge(21);

        RegistrationException exception = assertThrows(
                RegistrationException.class, () -> registrationService.register(secondUser));
        assertEquals("User with this login" + secondUser.getLogin()
                + "already exists", exception.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("pass");
        user.setAge(53);

        RegistrationException exception = assertThrows(
                RegistrationException.class, () -> registrationService.register(user));
        assertEquals("Login cannot be null!", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword(null);
        user.setAge(20);

        RegistrationException exception = assertThrows(
                RegistrationException.class, () -> registrationService.register(user));
        assertEquals("Password cannot be null!", exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User user1 = new User();
        user1.setLogin("");
        user1.setPassword("password");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("123");
        user2.setPassword("password");
        user2.setAge(20);

        User user3 = new User();
        user3.setLogin("user1");
        user3.setPassword("password");
        user3.setAge(20);

        RegistrationException exception1 = assertThrows(
                RegistrationException.class, () -> registrationService.register(user1));
        RegistrationException exception2 = assertThrows(
                RegistrationException.class, () -> registrationService.register(user2));
        RegistrationException exception3 = assertThrows(
                RegistrationException.class, () -> registrationService.register(user3));
        assertEquals("Login must be at least 6 characters long, you have "
                + user1.getLogin().length(), exception1.getMessage());
        assertEquals("Login must be at least 6 characters long, you have "
                + user2.getLogin().length(), exception2.getMessage());
        assertEquals("Login must be at least 6 characters long, you have "
                + user3.getLogin().length(), exception3.getMessage());
    }

    @Test
    void register_validLogin_ok() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("password");
        firstUser.setAge(20);

        User secondUser = new User();
        secondUser.setLogin("User12");
        secondUser.setPassword("password");
        secondUser.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(firstUser));
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_shortPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("");
        firstUser.setAge(20);

        User secondUser = new User();
        secondUser.setLogin("validUser2");
        secondUser.setPassword("abc");
        secondUser.setAge(20);

        User thirdUser = new User();
        thirdUser.setLogin("validUser3");
        thirdUser.setPassword("qwert");
        thirdUser.setAge(21);

        RegistrationException exception1 = assertThrows(
                RegistrationException.class, () -> registrationService.register(firstUser));
        RegistrationException exception2 = assertThrows(
                RegistrationException.class, () -> registrationService.register(secondUser));
        RegistrationException exception3 = assertThrows(
                RegistrationException.class, () -> registrationService.register(thirdUser));

        assertEquals("Password must be at least 6 characters, you have "
                + firstUser.getPassword().length(), exception1.getMessage());
        assertEquals("Password must be at least 6 characters, you have "
                + secondUser.getPassword().length(), exception2.getMessage());
        assertEquals("Password must be at least 6 characters, you have "
                + thirdUser.getPassword().length(), exception3.getMessage());
    }

    @Test
    void register_validPassword_ok() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("qwerty");
        firstUser.setAge(20);

        User secondUser = new User();
        secondUser.setLogin("validUser2");
        secondUser.setPassword("password");
        secondUser.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(firstUser));
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_ageUnder18_notOk() {
        User user1 = new User();
        user1.setLogin("validUser");
        user1.setPassword("password");
        user1.setAge(17);

        User user2 = new User();
        user2.setLogin("validUser");
        user2.setPassword("password");
        user2.setAge(null);

        User user3 = new User();
        user3.setLogin("validUser");
        user3.setPassword("password");
        user3.setAge(-4);

        RegistrationException exception1 = assertThrows(
                RegistrationException.class, () -> registrationService.register(user1));
        RegistrationException exception2 = assertThrows(
                RegistrationException.class, () -> registrationService.register(user2));
        RegistrationException exception3 = assertThrows(
                RegistrationException.class, () -> registrationService.register(user3));

        assertEquals("User must be at least 18 years old", exception1.getMessage());
        assertEquals("Age cannot be null!", exception2.getMessage());
        assertEquals("User must be at least 18 years old", exception3.getMessage());
    }

    @Test
    void register_validAge_ok() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("password");
        firstUser.setAge(18);

        User secondUser = new User();
        secondUser.setLogin("User12");
        secondUser.setPassword("password");
        secondUser.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(firstUser));
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_validUser_addedToStorage_ok() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("validPass123");
        user.setAge(22);

        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }
}
