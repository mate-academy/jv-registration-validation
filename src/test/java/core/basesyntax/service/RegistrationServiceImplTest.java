package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void beforeAll() {
        user1 = new User();
        user2 = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user1.setAge(18);
        user1.setPassword("password");
        user1.setLogin("login@gmail.com");
        user2.setAge(40);
        user2.setPassword("differentPassword");
        user2.setLogin("differentLogin@gmail.com");
    }

    @Test
    void register_userIsNull() {
        user1 = null;
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user1);
        });
        user1 = new User();
    }

    @Test
    void register_containsSameLogin_NotOk() {
        registrationService.register(user1);
        user2.setLogin(user1.getLogin());
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_containsDifferentLogin_Ok() {
        registrationService.register(user1);
        registrationService.register(user2);
        assertTrue(Storage.people.contains(user1));
        assertTrue(Storage.people.contains(user2));
    }

    @Test
    void register_containsNullLogin_NotOk() {
        user1.setLogin(null);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_olderThanEighteen_Ok() {
        registrationService.register(user1);
        assertTrue(Storage.people.contains(user1));
    }

    @Test
    void register_youngerThanEighteen_NotOk() {
        user1.setAge(12);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_ageIsNull_NotOk() {
        user1.setAge(null);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_passwordIsLongEnough_Ok() {
        registrationService.register(user1);
        assertTrue(Storage.people.contains(user1));
    }

    @Test
    void register_passwordIsNotLongEnough_NotOk() {
        user1.setPassword("small");
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user1.setPassword(null);
        assertThrows(RuntimeException.class, ()-> {
            registrationService.register(user1);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}