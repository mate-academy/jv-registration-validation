package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user1 = new User();
        user2 = new User();
        user1.setLogin("nafania");
        user1.setPassword("oldDogBob");
        user1.setAge(18);
        user1.setId(0L);
        user2.setLogin("nafania");
        user2.setPassword("oldDogBob");
        user2.setAge(18);
        user2.setId(0L);

    }

    @Test
    void register_ageUser_notOk() {
        user1.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_passwordUser_notOk() {
        user1.setPassword("hello");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullId_notOk() {
        user1.setId(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_notSuccess() {
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_Success() {
        user2.setLogin("finishTest");
        registrationService.register(user2);
    }
}
