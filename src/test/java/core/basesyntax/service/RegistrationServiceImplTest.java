package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final long ID = 2022;
    private static RegistrationService registerObject;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        registerObject = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setAge(18);
        validUser.setLogin("FirstUser");
        validUser.setPassword("password");
    }

    @Test
    void register_validUser_ok() {
        assertTrue(validUser.getAge() >= 18,
                "Age should be at least 18 years old");
        assertTrue(validUser.getPassword().length() >= 6,
                "Password should have at least 6 symbols");
        assertEquals(validUser, registerObject.register(validUser),
                "User have should to register");
    }

    @Test
    void register_userWithId_notOk() {
        User userWithId = new User();
        userWithId.setLogin("IdUser");
        userWithId.setAge(24);
        userWithId.setPassword("password");
        userWithId.setId(ID);
        assertThrows(RuntimeException.class,()-> registerObject.register(userWithId),
                "ID should set automatically");
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithInvalidLogin = new User();
        userWithInvalidLogin.setAge(19);
        userWithInvalidLogin.setPassword("password");
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidLogin),
                "User cannot be with null login!");
        userWithInvalidLogin.setLogin("");
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidLogin),
                "User cannot have empty login!");
    }

    @Test
    void register_password_notOk() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setAge(20);
        userWithInvalidPassword.setLogin("ThirdUser");
        assertThrows(RuntimeException.class, ()-> registerObject.register(userWithInvalidPassword),
                "Cannot registers user with null password!");
        userWithInvalidPassword.setPassword("start");
        assertThrows(RuntimeException.class, ()-> registerObject.register(userWithInvalidPassword),
                "Password should contain at least 6 symbols");
    }

    @Test
    void  register_invalidAge_notOk() {
        User userWithInvalidAge = new User();
        userWithInvalidAge.setPassword("password");
        userWithInvalidAge.setLogin("SecondUser");
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidAge),
                "User should have age!");
        userWithInvalidAge.setAge(-1);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidAge),
                "Age cannot less or equals 0!");
        userWithInvalidAge.setAge(13);
        assertThrows(RuntimeException.class, () -> registerObject.register(userWithInvalidAge),
                "User cannot have less than 18 years old!");
    }

    @Test
    void register_theSameLogin_notOk() {
        assertThrows(RuntimeException.class, () -> registerObject.register(validUser),
                "User exists with login:" + validUser.getLogin());
    }
}