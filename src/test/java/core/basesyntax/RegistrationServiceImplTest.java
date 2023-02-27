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
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User(123456L, "First", "qwert", 20);
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(123456L, "First", "qwerty", null);
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_youngAge_notOk() {
        User user = new User(123456L, "First", "qwerty", 16);
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExists_notOk() {
        User user = new User(123456L, "First", "qwerty", 21);
        storageDao.add(user);
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User(123456L, "", "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(123456L, null, "qwerty", 21);
        assertThrows(WrongDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_successRegister_ok() {
        User user = new User(123456L, "Ford", "qwerty", 21);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void get_user_ok() {
        User user = new User(123456L, "Second", "qwerty", 21);
        registrationService.register(user);
        User actual = storageDao.get("Second");
        assertEquals(user, actual);
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
        assertEquals(actual, 3);
    }

    @Test
    void equals_usersEqual_ok() {
        User user = new User(223456L, "Green", "qwerty", 24);
        User user1 = new User(223456L, "Green", "qwerty", 24);
        boolean actual = user.equals(user1);
        assertTrue(actual);
    }

    @Test
    void equals_usersNotEqual_NotOk() {
        User user = new User(223456L, "Green", "qwerty", 24);
        User user1 = new User(223456L, "Yellow", "qwerty", 24);
        boolean actual = user.equals(user1);
        assertFalse(actual);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(WrongDataException.class, () -> registrationService.register(null));
    }

    @Test
    void getId_returnId_ok() {
        Long expected = 444444L;
        User user = new User(expected, "Purple", "qwerty12", 24);
        Long actual = user.getId();
        assertEquals(actual, expected);

    }

    @Test
    void setLogin_set_ok() {
        String expected = "White";
        User user = new User(123456L, "Black", "qwerty12", 24);
        user.setLogin(expected);
        String actual = user.getLogin();
        assertEquals(actual, expected);
    }

    @Test
    void setPassword_set_ok() {
        String expected = "asdfgh";
        User user = new User(123456L, "Black", "qwerty12", 24);
        user.setPassword(expected);
        String actual = user.getPassword();
        assertEquals(actual, expected);
    }

    @Test
    void setAge_set_ok() {
        int expectedAge = 30;
        User user = new User(123456L, "Black", "qwerty12", 24);
        user.setAge(expectedAge);
        int actual = user.getAge();
        assertEquals(actual, expectedAge);
    }
}
