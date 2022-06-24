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
    void register_nullUser_notOk() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(null);
        });
        Assertions.assertEquals(thrown.getMessage(), "User cannot be null");
    }

    @Test
    void register_ok() {
        User user = new User();
        user.setAge(18);
        user.setId(12L);
        user.setLogin("User");
        user.setPassword("test123");
        Assertions.assertEquals(registerService.register(user), user);
    }

    @Test
    void register_emptyLogin_notOk() {
        stepan.setLogin("");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User login can not be empty");
    }

    @Test
    void register_loginIsNull_notOk() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(new User());
        });
        Assertions.assertEquals(thrown.getMessage(), "User login can not be null");
    }

    @Test
    void register_loginIsExists_notOk() {
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
    void register_ageMinAge_notOk() {
        stepan.setAge(15);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User age need to have 18 years");
    }

    @Test
    void register_ageIsNull_notOk() {
        stepan.setLogin("stepan");
        stepan.setAge(null);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User age can not be null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        stepan.setLogin("stepan");
        stepan.setAge(20);
        stepan.setPassword(null);
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User password can not be null");
    }

    @Test
    void register_passwordMinLength_notOk() {
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

    @Test
    void register_emptyPassword_notOk() {
        stepan.setLogin("SmallPassword");
        stepan.setAge(20);
        stepan.setPassword("");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            registerService.register(stepan);
        });
        Assertions.assertEquals(thrown.getMessage(), "User password can not be empty");
    }
}
