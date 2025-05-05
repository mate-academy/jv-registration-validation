package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationService service = new RegistrationServiceImpl();

    @BeforeEach
    @AfterEach
    void clearStorage() {
        people.clear();
    }

    @Test
    void registerUser_validData_userAddedToStorage() {
        User user = new User("ValidLogin", "ValidPassword", 20);
        User registeredUser = service.register(user);
        assertEquals(1, people.size());
        assertEquals(user, registeredUser);
    }

    @Test
    void registerUser_nullUser_throwsException() {
        assertThrows(InvalidDataException.class,
                () -> service.register(null), "User cannot be null.");
    }

    @Test
    void registerUser_duplicateLogin_throwsException() {
        User user1 = new User("DuplicateLogin", "Password1", 20);
        User user2 = new User("DuplicateLogin", "Password2", 25);
        people.add(user1);
        assertThrows(InvalidDataException.class,
                () -> service.register(user2), "User with this login is already registered.");
    }

    @Test
    void registerUser_nullLogin_throwsException() {
        User user = new User(null, "Password", 20);
        assertThrows(InvalidDataException.class,
                () -> service.register(user), "User's login cannot be null or empty.");
    }

    @Test
    void registerUser_nullPassword_throwsException() {
        User user = new User("Login", null, 20);
        assertThrows(InvalidDataException.class,
                () -> service.register(user), "User's password cannot be null or empty.");
    }

    @Test
    void registerUser_shortLogin_throwsException() {
        User user = new User("Short", "Password", 20);
        assertThrows(InvalidDataException.class,
                () -> service.register(user), "User's login must be at least 6 characters long.");
    }

    @Test
    void registerUser_shortPassword_throwsException() {
        User user = new User("Login", "Short", 20);
        assertThrows(InvalidDataException.class,
                () -> service.register(user),
                "User's password must be at least 6 characters long.");
    }

    @Test
    void registerUser_ageBelowMinimum_throwsException() {
        User user = new User("Login", "Password", 17);
        assertThrows(InvalidDataException.class,
                () -> service.register(user), "User's age must be at least 18 years.");
    }

    @Test
    void registerUser_nullAge_throwsException() {
        User user = new User("Login", "Password", null);
        assertThrows(InvalidDataException.class,
                () -> service.register(user), "User's age must be at least 18 years.");
    }
}
