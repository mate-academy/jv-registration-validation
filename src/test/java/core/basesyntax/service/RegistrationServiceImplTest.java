package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService regServ;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        regServ = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("UserLogin");
        user.setPassword("Pass123");
        user.setAge(25);
    }

    @Test
    void register_null_ThrowException_Ok() {
        try {
            regServ.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if User is null");
    }

    @Test
    void register_UserLoginNull_ThrowException_ok() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown if User login is null");
    }

    @Test
    void register_UserEmptyLogin_ThrowException_ok() {
        user.setLogin(" ");
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown if User login is empty");
    }

    @Test
    void register_UserPasswordNull_ThrowException_ok() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown if User password is null");
    }

    @Test
    void registerUser_WithEmptyPassword_ThrowException_ok() {
        user.setPassword(" ");
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown if User password is empty");
    }

    @Test
    void registerUser_when_PasswordLengthLessThan6_NotOk() {
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown "
                        + "if User password length less than 6 character");
    }

    @Test
    void registerUser_when_AgeLessThan18_NotOk() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown if User age less than 18");
    }

    @Test
    void register_SameUser_Should_ThrowException_ok() {
        regServ.register(user);
        assertThrows(RuntimeException.class, () -> regServ.register(user),
                "RuntimeException should be thrown if User already exist in storageDao");
    }
}
