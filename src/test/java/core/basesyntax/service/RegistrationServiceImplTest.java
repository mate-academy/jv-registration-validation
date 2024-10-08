package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.UserExistException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        User user = new User(null, "Qwerty", 25);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("stark", "Qwerty", 25);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User("t.stark", null, 25);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("t.stark", "Qwe", 25);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User("t.stark", "Qwerty", null);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User("t.stark", "Qwerty", 15);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExist_notOk() {
        User firstUser = new User("t.stark", "Qwerty", 25);
        User secondUser = new User("t.stark", "Qwerty12345", 20);

        registrationService.register(firstUser);

        assertThrows(UserExistException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_validUser_shouldReturnUser() {
        User user = new User("t.stark", "Qwerty", 25);

        User createdUser = registrationService.register(user);

        assertNotNull(createdUser.getId());
        assertEquals(user.getLogin(), createdUser.getLogin());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getAge(), createdUser.getAge());
    }
}
