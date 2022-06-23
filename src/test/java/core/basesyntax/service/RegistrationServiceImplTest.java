package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User stepan;
    private static RegistrationServiceImpl registerService;

    @BeforeAll
    static void init() {
        registerService = new RegistrationServiceImpl();
        stepan = new User();
        stepan.setAge(18);
        stepan.setId(12L);
        stepan.setLogin("Stepan");
        stepan.setPassword("test123");
    }

    @Test
    void checkRegister_null_notOk() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(null);
        });
        Assertions.assertEquals(thrown.getMessage(), "User cannot be null");
    }

    @Test
    void validRegister_ok() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("User");
        user.setPassword("test123");
        Assertions.assertEquals(registerService.register(user), user);
    }

    @Test
    void check_emptyLogin_notOk() {
        stepan.setLogin("");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User login can not be empty");
    }

    @Test
    void check_loginIsNull_notOk() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(new User());
        });
        Assertions.assertEquals(thrown.getMessage(), "User login can not be null");
    }

    @Test
    void check_loginIsExists_notOk() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("TestUser");
        user.setPassword("test123");
        registerService.register(user);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(user);
        });
        Assertions.assertEquals(thrown.getMessage(), "User with such login already exists");
    }

    @Test
    void check_ageIsNull_notOk() {
        stepan.setAge(null);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User age can not be null");
    }

    @Test
    void check_ageMinAge_notOk() {
        stepan.setAge(15);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User age need to have 18 years");
    }

    @Test
    void check_passwordIsNull_notOk() {
        stepan.setLogin("stepan");
        stepan.setAge(20);
        stepan.setPassword(null);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User password can not be null");
    }

    @Test
    void check_emptyPassword_notOk() {
        stepan.setPassword("");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User password can not be empty");
    }

    @Test
    void check_passwordMinLength_notOk() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("SmallPassword");
        user.setPassword("123");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(user);
        });
        Assertions.assertEquals(thrown.getMessage(), "Length of password is less than 6");
    }
}
