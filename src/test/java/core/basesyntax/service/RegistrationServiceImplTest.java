package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User correctUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser = new User("user1", "passIsOk", 21);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_newCorrectUser_checkSize_OK() {
        registrationService.register(correctUser);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_newCorrectUserReturn_OK() {
        assertTrue(registrationService.register(correctUser).equals(correctUser));
    }

    @Test
    void register_newUnCorrectUserPassword_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user5", "111", 21));
        });
    }

    @Test
    void register_newUnCorrectUserAge_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user6", "123456", 15));
        });
    }

    @Test
    void register_UserAlreadyExist_notOK() {
        registrationService.register(correctUser);
        User equalLoginUser = new User("user1", "passIsOk2", 33);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(equalLoginUser);
        });
    }

    @Test
    void register_UserLoginIsNull_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(null, "123456", 18));
        });
    }

    @Test
    void register_UserPasswordIsNull_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user7", null, 20));
        });
    }

    @Test
    void register_UserAgeIsNull_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("user8", "null123", null));
        });
    }
}