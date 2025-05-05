package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullPassword_notOk() {
        User first = new User("First", null, 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(first),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " Your password cannot be null!");
    }

    @Test
    void register_shortPassword_notOk() {
        User first = new User("First", "qwert", 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(first),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + "Your password cannot be less than " + 6 + " characters!");
    }

    @Test
    void register_tooLongPassword_notOk() {
        User first = new User("First", "qwertyqwerty1", 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(first),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + "Your password cannot be longer than " + 12 + " characters!");
    }

    @Test
    void register_nullAge_notOk() {
        User first = new User("First", "qwerty", null);
        assertThrows(WrongDataException.class, () -> registrationService.register(first),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " Your age cannot be null!");
    }

    @Test
    void register_youngAge_notOk() {
        User first = new User("First", "qwerty", 16);
        assertThrows(WrongDataException.class, () -> registrationService.register(first),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + "Your age cannot be less than " + 18 + " years!");
    }

    @Test
    void register_oldAge_notOk() {
        User black = new User("black", "qwerty", 101);
        assertThrows(WrongDataException.class, () -> registrationService.register(black),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + "Your age cannot be more than " + 100 + " years!");
    }

    @Test
    void register_userExists_notOk() {
        User first = new User("First", "qwerty", 21);
        storageDao.add(first);
        assertThrows(WrongDataException.class, () -> registrationService.register(first),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " You cannot register user with the same login!");
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User("", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " Login cannot be empty!");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " Login cannot be null!");
    }

    @Test
    void register_tooLongLogin_notOk() {
        User user = new User("Polischuck", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " Login must contain no more " + 6 + " characters");
    }

    @Test
    void register_loginWithSpace_notOk() {
        User user = new User(" Polis", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " Login cannot contain spaces!");
    }

    @Test
    void register_successRegister_ok() {
        User ford = new User("Ford", "qwerty", 21);
        User actual = registrationService.register(ford);
        assertEquals(ford, actual, "Registration must be successful!");
    }

    @Test
    void get_user_ok() {
        User second = new User("Second", "qwerty", 21);
        registrationService.register(second);
        User actual = storageDao.get("Second");
        assertEquals(second, actual, "The method must return " + second);
    }

    @Test
    void register_severalUsers_ok() {
        User red = new User("Red", "qwerty", 21);
        User blue = new User("Blue", "qwe123", 27);
        User yellow = new User("Yellow", "123rty", 29);
        registrationService.register(red);
        registrationService.register(blue);
        registrationService.register(yellow);
        int actual = Storage.people.size();
        assertEquals(actual, 3,
                "Incorrect number of users! Expected " + 3 + ", but was " + actual);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(WrongDataException.class, () -> registrationService.register(null),
                "Test failed! Expected " + WrongDataException.class.getName()
                        + " User cannot be null!");
    }
}
