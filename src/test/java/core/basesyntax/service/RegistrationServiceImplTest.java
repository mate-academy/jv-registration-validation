package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.UserNotAllowed;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
     void setup() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin("volodymyr.nyshta@gmail.com");
        user.setAge(21);
        user.setPassword("123Test");
    }

    @Test
    void register_validUser_Ok() {
        User actual = new User();
        actual.setAge(20);
        actual.setPassword("asdfasdf");
        actual.setLogin("testtesttest@gmail.com");
        User registeredUser = registrationService.register(actual);
        assertEquals(registeredUser, actual);
    }

    @Test
    void register_userWithTheSameLoginAlreadyExists_NotOk() {
        User user1 = new User();
        user1.setAge(18);
        user1.setLogin("volodymyrtest.nyshta@gmail.com");
        user1.setPassword("1234567");
        registrationService.register(user1);
        User user2 = new User();
        user2.setAge(18);
        user2.setLogin("volodymyrtest.nyshta@gmail.com");
        user2.setPassword("1234567");
        registrationService.register(user);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(user2));
    }

    @Test
    void userNullNotOk() {
        assertThrows(UserNotAllowed.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginLengthLessThan6Characters_notOk() {
        User actual = user;
        actual.setLogin("test");
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_loginNull_notOk() {
        User actual = user;
        actual.setLogin(null);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_emptylogin_notOk() {
        User actual = user;
        actual.setLogin("");
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordLengthLessThan6Characters_notOk() {
        User actual = user;
        actual.setPassword("test");
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User actual = user;
        actual.setPassword(null);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_emptyPassword_notOk() {
        User actual = user;
        actual.setPassword("");
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_ageIsNull_notOk() {
        User actual = user;
        actual.setAge(null);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_negativeAge_notOk() {
        User actual = user;
        actual.setAge(-1);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_ageLessThan_18_notOk() {
        User actual = user;
        actual.setAge(12);
        assertThrows(UserNotAllowed.class, () -> registrationService.register(actual));
    }

    @Test
    void register_FildShuldNotBeNullAfterRegister() {
        User actual = new User();
        actual.setAge(19);
        actual.setLogin("volodymyrTest");
        actual.setPassword("123456qwe");
        User registeredUser = registrationService.register(actual);
        assertNotNull(registeredUser.getAge());
        assertNotNull(registeredUser.getLogin());
        assertNotNull(registeredUser.getPassword());
    }
}
