package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User firstUser;
    private static User secondUser;

    @BeforeAll
    static void beforeAll() {
        firstUser = new User();
        secondUser = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser.setAge(18);
        firstUser.setPassword("password");
        firstUser.setLogin("login@gmail.com");
        secondUser.setAge(40);
        secondUser.setPassword("differentPassword");
        secondUser.setLogin("differentLogin@gmail.com");
    }

    @Test
    void register_userIsNull() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_containsSameLogin_notOk() {
        registrationService.register(firstUser);
        secondUser.setLogin(firstUser.getLogin());
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_containsDifferentLogin_Ok() {
        assertDoesNotThrow(()-> {
            registrationService.register(firstUser);
        });
        assertDoesNotThrow(()-> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_containsNullLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_olderThanEighteen_Ok() {
        registrationService.register(firstUser);
        assertTrue(Storage.people.contains(firstUser));
    }

    @Test
    void register_youngerThanEighteen_notOk() {
        firstUser.setAge(17);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_passwordIsLongEnough_Ok() {
        registrationService.register(firstUser);
        assertTrue(Storage.people.contains(firstUser));
    }

    @Test
    void register_passwordIsNotLongEnough_notOk() {
        firstUser.setPassword("12345");
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(firstUser);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
