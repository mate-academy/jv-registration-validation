package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User testUser1;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        testUser1 = new User();
        testUser1.setId(1L);
    }

    @Test
    void register_nullUser_notOk() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("User value can't be Null");
    }

    @Test
    void register_nullAge_notOk() {
        try {
            testUser1.setPassword("1234567");
            testUser1.setLogin("testUser1");
            testUser1.setAge(null);
            registrationService.register(testUser1);
        } catch (RuntimeException e) {
            return;
        }
        fail("User age can't be Null");
    }

    @Test
    void register_nullLogin_notOk() {
        try {
            testUser1.setPassword("1234567");
            testUser1.setLogin(null);
            testUser1.setAge(19);
            registrationService.register(testUser1);
        } catch (RuntimeException e) {
            return;
        }
        fail("User login can't be Null");
    }

    @Test
    void register_nullPassword_notOk() {
        try {
            testUser1.setPassword(null);
            testUser1.setLogin("User");
            testUser1.setAge(19);
            registrationService.register(testUser1);
        } catch (RuntimeException e) {
            return;
        }
        fail("User password can't be Null");
    }

    @Test
    void register_less18Age_notOk() {
        testUser1.setPassword("12345678");
        testUser1.setLogin("User");
        testUser1.setAge(17);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_less6PassLength_notOk() {
        testUser1.setPassword("12345");
        testUser1.setLogin("User");
        testUser1.setAge(20);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser1);
        });
    }

    @Test
    void register_issetLogin_notOk() {
        testUser1.setPassword("12345678");
        testUser1.setLogin("User1");
        testUser1.setAge(20);
        User testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setAge(35);
        testUser2.setPassword("qawsedrftg");
        testUser2.setLogin("User1");
        registrationService.register(testUser1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(testUser2);
        });
    }
}
