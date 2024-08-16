package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUpOnce() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void shouldThrowException_whenUserIsNull() {
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(null);
        }, "Expected UserDataInvalidException when user is null");
    }

    @Test
    void shouldThrowException_whenLoginIsNull() {
        User user = new User();
        user.setPassword("validPassword123");
        user.setAge(21);
        user.setLogin(null);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when login is null");
    }

    @Test
    void shouldThrowException_whenLoginIsEmpty() {
        User user = new User();
        user.setPassword("validPassword123");
        user.setAge(21);
        user.setLogin("");
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when login is empty");
    }

    @Test
    void shouldThrowException_whenLoginIsTooShort() {
        User user = new User();
        user.setPassword("validPassword123");
        user.setAge(21);
        user.setLogin("12345");
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException for login length less than required minimum");
    }

    @Test
    void shouldThrowException_whenPasswordIsNull() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(21);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when password is null");
    }

    @Test
    void shouldThrowException_whenPasswordIsEmpty() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("");
        user.setAge(21);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when password is empty");
    }

    @Test
    void shouldThrowException_whenPasswordIsTooShort() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(21);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when password length is less than required minimum");
    }

    @Test
    void shouldThrowException_whenAgeIsNull() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword123");
        user.setAge(null);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when age is null");
    }

    @Test
    void shouldThrowException_whenAgeIsNegative() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword123");
        user.setAge(-25);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when age is negative");
    }

    @Test
    void shouldThrowException_whenAgeIsBelowMinimum() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword123");
        user.setAge(17);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when age is below minimum required");
    }

    @Test
    void shouldThrowException_whenLoginAlreadyExists() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword123");
        user.setAge(50);
        Storage.people.add(user);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException when login already exists in storage");
    }

    @Test
    void shouldRegisterValidUser() {
        User expected = new User();
        expected.setLogin("validLogin");
        expected.setPassword("validPassword123");
        expected.setAge(50);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "Expected the registered user to match the input user data");
    }

    @Test
    void shouldRegisterUser_whenAllFieldsAreAtEdgeCase() {
        User expected = new User();
        expected.setLogin("login6");
        expected.setPassword("123456");
        expected.setAge(18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "Expected the registered user to match the input user data for edge case");
    }

    @Test
    void shouldAddUserToStorage_whenRegistrationIsSuccessful() {
        User expected = new User();
        expected.setLogin("validLogin");
        expected.setPassword("validPassword123");
        expected.setAge(50);
        User actual = registrationService.register(expected);
        assertTrue(Storage.people.contains(actual),
                "Expected the storage to contain the registered user");
    }

    @Test
    void shouldNotAddUserToStorage_whenRegistrationFails() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPassword123");
        user.setAge(50);
        assertThrows(UserDataInvalidException.class, () -> {
            registrationService.register(user);
        }, "Expected UserDataInvalidException for user with null login");
        assertFalse(Storage.people.contains(user),
                "Expected the storage not to contain the user when registration fails");
    }
}
