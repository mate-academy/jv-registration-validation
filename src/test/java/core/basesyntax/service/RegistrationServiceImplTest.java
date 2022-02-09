package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl service = new RegistrationServiceImpl();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("Nazarii");
        testUser.setPassword("qwerty123");
        testUser.setAge(28);
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_tooYoung_notOk() {
        testUser.setAge(15);
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_18years_Ok() {
        testUser.setAge(18);
        User user = service.register(testUser);
        Assertions.assertEquals(user, testUser);
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_tooShortPassword_notOk() {
        testUser.setPassword("qwert");
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
        void register_validData_Ok() {
        User user = service.register(testUser);
        Assertions.assertEquals(testUser, user);
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(testUser);
        User user = new User();
        user.setLogin("Nazarii");
        user.setPassword("1234qwerasd");
        user.setAge(21);
        Assertions.assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
