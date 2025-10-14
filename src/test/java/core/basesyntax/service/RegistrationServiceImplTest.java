package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private StorageDaoImpl dao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        dao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(dao);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User testUser = new User(1L, null, "PasswordOk", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User testUser = new User(1L, "mee", "PasswordOk", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        User testUser = new User(1L, "", "PasswordOk", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_whitespaceLogin_notOk() {
        User testUser = new User(1L, "     ", "PasswordOk", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User testUser = new User(1L, "LoginOk", null, 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_shortPassword_notOk() {
        User testUser = new User(1L, "LoginOk", "bad", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        User testUser = new User(1L, "LoginOk", "", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_whitespacePassword_notOk() {
        User testUser = new User(1L, "LoginOk", "      ", 20);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_loginAndPasswordExactly6_ok() {
        User testUser = new User(1L, "Loginn", "Passwo", 20);
        User registered = registrationService.register(testUser);
        assertNotNull(registered);
        assertEquals(testUser, registered);
        assertEquals(testUser, dao.get(testUser.getLogin()));
        assertEquals("Loginn", registered.getLogin());
    }

    @Test
    void register_login_ok() {
        User testUser = new User(1L, "LoginOk", "PasswordOk", 20);
        User registered = registrationService.register(testUser);
        assertNotNull(registered);
        assertEquals(testUser, registered);
        assertEquals(testUser, dao.get(testUser.getLogin()));
    }

    @Test
    void register_password_ok() {
        User testUser = new User(1L, "LoginOk", "passwor", 20);
        User registered = registrationService.register(testUser);
        assertNotNull(registered);
        assertEquals(testUser, registered);
        assertEquals(testUser, dao.get(testUser.getLogin()));
    }

    @Test
    void register_age17_notOk() {
        User testUser = new User(1L, "LoginOk", "PasswordOk", 17);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_age18_ok() {
        User testUser = new User(1L, "LoginOk", "PasswordOk", 18);
        User registered = registrationService.register(testUser);
        assertNotNull(registered);
        assertEquals(testUser, dao.get(testUser.getLogin()));
    }

    @Test
    void register_underage_notOk() {
        User testUser = new User(1L, "LoginOk", "PasswordOk", 16);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User testUser1 = new User(1L, "LoginOk", "PasswordOk", 20);
        User testUser2 = new User(13L, "LoginOk", "Passwordko", 22);
        dao.add(testUser1);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(testUser2));
    }

    @Test
    void register_validUser_ok() {
        User testUser = new User(12L, "LoginGood", "PasswordGood", 19);
        User registered = registrationService.register(testUser);
        assertNotNull(registered);
        assertEquals(testUser, registered);
        assertEquals(testUser, dao.get(testUser.getLogin()));
        assertEquals("LoginGood", registered.getLogin());
        assertEquals("PasswordGood", registered.getPassword());
        assertEquals(19, registered.getAge());
    }
}
