package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User firstUser;
    private static User secondUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        secondUser = new User();
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
    void register_userIsExists_notOk() {
        assertThrows(RuntimeException.class, () -> {
            assertEquals(firstUser.getLogin(), registrationService.register(firstUser).getLogin());
        });
    }

    @Test
    void register_userDoesNotExists_Ok() {
        assertNotEquals(secondUser, firstUser);
    }

    @Test
    void register_ageLessThen_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser).getAge();
        });
    }

    @Test
    void register_passwordContainsLessThen_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser).getPassword();
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}

