package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.UserNotAllowed;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setLogin("volodymyr.nyshta@gmail.com");
        user.setAge(21);
        user.setPassword("123Test");
    }

    @Test
    void isCorrectType() {
        assertTrue(user instanceof User);
    }

    @Test
    void userWithTheSameLoginAlreadyExists_NotOk() {
        User user2 = new User();
        user2.setAge(18);
        user2.setLogin("volodymyr.nyshta@gmail.com");
        user2.setPassword("1234567");
        registrationService.register(user);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(user2));
    }

    @Test
    void userNullNotOk() {
        User actual = registrationService.register(null);
        assertNull(actual);
    }

    @Test
    void loginIsNotOk() {
        User actual = user;
        actual.setLogin("test");
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void ageLessThan_18_NotOk() {
        User actual = user;
        actual.setAge(12);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void passwordLengthLessThan_6_characters_NotOk() {
        User actual = user;
        actual.setPassword("test");
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void passwordIsNull_NotOk() {
        User actual = user;
        actual.setPassword(null);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void ageIsNull_NotOk() {
        User actual = user;
        actual.setAge(null);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }
}
