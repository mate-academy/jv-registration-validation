package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static Storage storage;
    private static User firstUser;
    private static User secondUser;
    private static User thirdUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
        firstUser = new User();
        secondUser = new User();
        thirdUser = new User();
    }

    @BeforeEach
    void setUp() {
        firstUser.setLogin("angela16081988");
        firstUser.setAge(18);
        firstUser.setPassword("Qwerty");
        secondUser.setLogin("angela16");
        secondUser.setAge(16);
        secondUser.setPassword("1234L");
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        thirdUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        thirdUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        thirdUser.setPassword("");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        thirdUser.setLogin("");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_nullAge_notOk() {
        thirdUser.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_negativeAge() {
        thirdUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_userIsExists_notOk() {
        firstUser.setLogin("Qwerty16");
        thirdUser.setLogin("Qwerty16");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(thirdUser);
        });
    }

    @Test
    void register_ageLessThen_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_passwordContainsLessThen_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
