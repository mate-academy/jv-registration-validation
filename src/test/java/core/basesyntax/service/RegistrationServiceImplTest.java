package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static User user1;
    private static User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user2 = new User();
        user1.setAge(35);
        user1.setLogin("Chuchu");
        user1.setPassword("43jk43jk43");
        Storage.people.clear();
    }

    @Test
    void userRegister_OK() {
        registrationService.register(user1);
        assertTrue(Storage.people.contains(user1));
    }


    @Test
    void register_nullAge_notOk() {
        user2.setLogin("test");
        user2.setPassword("jk43");
        assertThrows(RuntimeException.class, () ->  registrationService.register(user2));
    }

    @Test
    void register_userAgeLessThan_18_notOk() {
        user2.setAge(2);
        user2.setLogin("test");
        user2.setPassword("jk4ygtr3");
        assertThrows(RuntimeException.class, () ->  registrationService.register(user2));
    }

    @Test
    void register_passwordLessThan_6_notOk() {
        user2.setAge(20);
        user2.setLogin("test");
        user2.setPassword("jtr3");
        assertThrows(RuntimeException.class, () ->  registrationService.register(user2));
    }

    @Test
    void register_nullPassword_notOk() {
        user2.setAge(40);
        user2.setLogin("test");
        assertThrows(RuntimeException.class, () ->  registrationService.register(user2));
    }

    @Test
    void register_loginHasAlready_notOk() {
        user2.setAge(22);
        user2.setLogin("Chuchu");
        user2.setPassword("343422rfr");
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () ->  registrationService.register(user2));
    }
}

