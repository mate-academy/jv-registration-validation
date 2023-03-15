package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static List<User> users;

    @BeforeAll
    static void initializeVariables() {
        registrationService = new RegistrationServiceImpl();
        users = new ArrayList<>();
    }

    @BeforeEach
    void fillCollection() {
        User user1 = new User(1L, "login1", "password1", 20);
        User user2 = new User(2L, "login2", "password2", 17);
        User user3 = new User(3L, "login3", "123", 35);
        User user4 = new User(4L, "login4", "1234567", 25);
        User user5 = new User(5L, "login4", "1234567", 27);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
    }

    @Test
    void register_checkIfContains_isOk() {
        registrationService.register(users.get(0));
        assertTrue(Storage.people.contains(users.get(0)));
    }

    @Test
    void register_invalidAge_isNotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(1));
        });
    }

    @Test
    void register_invalidPassword_isNotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(2));
        });
    }

    @Test
    void register_existingLogin_isNotOk() {
        registrationService.register(users.get(3));
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(4));
        });
    }

}
