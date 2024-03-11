package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String INVALID_LOGIN = "user";
    private static final String VALID_LOGIN = "user1986";
    private static final String INVALID_PASSWORD = "pass";
    private static final String VALID_PASSWORD = "password";
    private static final String EXIST_LOGIN = "bob1989";
    private static final int INVALID_AGE = 15;
    private static final int VALID_AGE = 25;
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin(EXIST_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_UserIsValid() {
        User actualUser = registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(null)).getMessage();
        assertEquals("User can't be null", actual);
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user)).getMessage();
        assertEquals("Login can't be null", actual);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user)).getMessage();
        assertEquals("Age can't be null", actual);

    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user)).getMessage();
        assertEquals("Password can't be null", actual);

    }

    @Test
    void register_ageLess18_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user)).getMessage();
        assertEquals("Age can't be less than 18", actual);

    }

    @Test
    void register_PasswordLengthLess6Chars_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user)).getMessage();
        assertEquals("Password's length can't be less than 6 characters", actual);
    }

    @Test
    void register_LoginLengthLess6Chars_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        String actual = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user)).getMessage();
        assertEquals("Login can't be  less than 6 characters", actual);
    }

    @Test
    void register_ExistUser_notOk() {
        User bob = new User();
        bob.setLogin(EXIST_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(bob));
    }
}
