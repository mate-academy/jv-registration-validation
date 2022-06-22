package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static User stepan;
    private RegistrationServiceImpl registerService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        stepan = new User();
        stepan.setAge(18);
        stepan.setId(12L);
        stepan.setLogin("Stepan");
        stepan.setPassword("test123");
    }

    @Test
    void checkRegisterNull() {
        try {
            registerService.register(null);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User cannot be null");
        }
    }

    @Test
    void validRegister() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("User");
        user.setPassword("test123");
        Assertions.assertEquals(registerService.register(user), user);
    }

    @Test
    void checkEmptyLogin() {
        stepan.setLogin("");
        try {
            registerService.register(stepan);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User login can not be empty");
        }
    }

    @Test
    void checkLoginIsNull() {
        try {
            registerService.register(new User());
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User login can not be null");
        }
    }

    @Test
    void checkLoginIsExists() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("TestUser");
        user.setPassword("test123");
        registerService.register(user);
        try {
            registerService.register(user);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User with such login already exists");
        }
    }

    @Test
    void checkAgeIsNull() {
        stepan.setAge(null);
        try {
            registerService.register(stepan);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User age can not be null");
        }
    }

    @Test
    void checkAgeMinAge() {
        stepan.setAge(15);
        try {
            registerService.register(stepan);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User age need to have 18 years");
        }
    }

    @Test
    void checkPasswordIsNull() {
        stepan.setPassword(null);
        try {
            registerService.register(stepan);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User password can not be null");
        }
    }

    @Test
    void checkEmptyPassword() {
        stepan.setPassword("");
        try {
            registerService.register(stepan);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "User password can not be empty");
        }
    }

    @Test
    void checkPasswordMinLength() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("SmallPassword");
        user.setPassword("123");
        try {
            registerService.register(user);
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getMessage(), "Length of password is less than 6");
        }
    }
}
