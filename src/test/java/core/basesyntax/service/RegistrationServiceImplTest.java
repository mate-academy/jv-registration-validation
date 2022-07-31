package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private static User DEFAULT_USER;
    private static User YOUNG_USER;

    private User NULL_USER = new User();

    @BeforeAll
    static void beforeAll() {
        DEFAULT_USER = new User();
        DEFAULT_USER.setLogin("Kate");
        DEFAULT_USER.setId(123456789L);
        DEFAULT_USER.setAge(18);
        DEFAULT_USER.setPassword("parol1");


    }



    @Test
    void addSameLoginUser_NotOk() {
        registrationService.register(DEFAULT_USER);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addUserYanger18_NotOk() {
        DEFAULT_USER.setAge(10);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
        DEFAULT_USER.setAge(-5);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
        DEFAULT_USER.setAge(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addInvalidUserPassword_NotOk() {
        DEFAULT_USER.setPassword("");
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
        DEFAULT_USER.setPassword("1111");
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
        DEFAULT_USER.setPassword(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(NULL_USER));
    }
}