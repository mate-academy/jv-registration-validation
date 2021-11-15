package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static RegistrationService registrationServise;
    private static User bob;
    private static User alise;

    @BeforeAll
    static void beforeAll() {
        registrationServise = new RegistrationServiceImpl();
        bob = new User();
        alise = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        bob.setId(11L);
        bob.setLogin("Volodymyr");
        bob.setPassword("password");
        bob.setAge(21);
        alise.setId(12L);
        alise.setLogin("1abcdef");
        alise.setPassword("password");
        alise.setAge(20);
    }

    @Test
    void register_correctUser_isOk() {
        assertTrue(bob.equals(registrationServise.register(bob)));
    }

    @Test
    void register_loginStartWithNumeric_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationServise.register(alise);
        });
    }

    @Test
    void register_loginLengthLessThenThree_notOk() {
        alise.setLogin("al");
        assertThrows(RuntimeException.class, () -> {
            registrationServise.register(alise);
        });
    }

    @Test
    void register_nullAge_notOk() {
        bob.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_nullId_notOk() {
        bob.setId(null);
        assertThrows(NullPointerException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        bob.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        bob.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        bob.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        bob.setPassword("abcd");
        assertThrows(RuntimeException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_theSameUser_notOk() {
        registrationServise.register(bob);
        assertThrows(RuntimeException.class, () -> {
            registrationServise.register(bob);
        });
    }

    @Test
    void register_UserWithExistingLogin_notOk() {
        registrationServise.register(bob);
        alise.setLogin("Volodymyr");
        assertThrows(RuntimeException.class, () -> {
            registrationServise.register(alise);
        });
    }
}
