package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_nullPassword_notOk() {
        User user = new User(123456L, "First", null, 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Password cannot be null!");
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User(123456L, "First", "qwert", 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "The password must contain no less than 6 characters!");
    }

    @Test
    void register_tooLongPassword_notOk() {
        User user = new User(123456L, "First", "qwertyqwerty1", 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "The password must contain no more than 12 characters!");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(123456L, "First", "qwerty", null);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Age cannot be null!");
    }

    @Test
    void register_youngAge_notOk() {
        User user = new User(123456L, "First", "qwerty", 16);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Age must be at least 18 years!");
    }

    @Test
    void register_oldAge_notOk() {
        User user = new User(123456L, "Last", "qwerty", 101);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Age must must be no more 100 years!");
    }

    @Test
    void register_userExists_notOk() {
        User user = new User(123456L, "First", "qwerty", 21);
        storageDao.add(user);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "A user with this login already exists!");
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User(123456L, "", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Login cannot be empty!");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(123456L, null, "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Login cannot be null!");
    }

    @Test
    void register_toLongLogin_notOk() {
        User user = new User(123456L, "Polischuck", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Login must contain no more 6 characters!");
    }

    @Test
    void register_loginWithSpace_notOk() {
        User user = new User(123456L, " Polis", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user),
                "Login must not contain spaces!");
    }

    @Test
    void register_successRegister_ok() {
        User user = new User(123456L, "Ford", "qwerty", 21);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "Registration must be successful!");
    }

    @Test
    void get_user_ok() {
        User user = new User(123456L, "Second", "qwerty", 21);
        registrationService.register(user);
        User actual = storageDao.get("Second");
        assertEquals(user, actual,
                "The method must return " + user);
    }

    @Test
    void storage_countOfUser_ok() {
        User user = new User(123456L, "Red", "qwerty", 21);
        User user1 = new User(123457L, "Blue", "qwe123", 27);
        User user2 = new User(123458L, "Yellow", "123rty", 29);
        registrationService.register(user);
        registrationService.register(user1);
        registrationService.register(user2);
        int actual = Storage.people.size();
        assertEquals(actual, 3,
                "Incorrect number of users!");
    }

    @Test
    void equals_usersEqual_ok() {
        User user = new User(223456L, "Green", "qwerty", 24);
        User user1 = new User(223456L, "Green", "qwerty", 24);
        boolean actual = user.equals(user1);
        assertTrue(actual,
                "Users must be equal!");
    }

    @Test
    void equals_usersNotEqual_NotOk() {
        User user = new User(223456L, "Green", "qwerty", 24);
        User user1 = new User(223456L, "Yellow", "qwerty", 24);
        boolean actual = user.equals(user1);
        assertFalse(actual,
                "Users must be not equal!");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(WrongDataException.class, () -> registrationService.register(null),
                "User cannot be null!");
    }

    @Test
    void getId_returnId_ok() {
        Long expected = 444444L;
        User user = new User(expected, "Purple", "qwerty12", 24);
        Long actual = user.getId();
        assertEquals(actual, expected,
                "Method must return id " + expected);

    }

    @Test
    void setLogin_ok() {
        String expected = "White";
        User user = new User(123456L, "Black", "qwerty12", 24);
        user.setLogin(expected);
        String actual = user.getLogin();
        assertEquals(actual, expected,
                "Method must return login " + expected);
    }

    @Test
    void setPassword_ok() {
        String expected = "asdfgh";
        User user = new User(123456L, "Black", "qwerty12", 24);
        user.setPassword(expected);
        String actual = user.getPassword();
        assertEquals(actual, expected,
                "Method must return password " + expected);
    }

    @Test
    void setAge_ok() {
        int expectedAge = 30;
        User user = new User(123456L, "Black", "qwerty12", 24);
        user.setAge(expectedAge);
        int actual = user.getAge();
        assertEquals(actual, expectedAge,
                "Method must return age " + expectedAge);
    }
}
