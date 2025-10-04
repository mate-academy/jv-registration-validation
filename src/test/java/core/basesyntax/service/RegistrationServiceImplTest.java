package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_loginTooShort_3char_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("validPassword");
        user.setAge(30);

        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Login can't be lower than " + MIN_LOGIN_LENGTH, actual.getMessage());
    }

    @Test
    void register_loginTooShort_5char_notOk() {
        User user = new User();
        user.setLogin("abcdf");
        user.setPassword("validPassword");
        user.setAge(30);

        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Login can't be lower than " + MIN_LOGIN_LENGTH, actual.getMessage());
    }

    @Test
    void register_loginIsEmpty_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("alghkregohi");
        user.setAge(19);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Login can't be empty or null", actual.getMessage());
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("akaufrygak");
        user.setAge(73);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Login can't be empty or null", actual.getMessage());
    }

    @Test
    void register_passwordTooShort_3char_notOk() {
        User user = new User();
        user.setLogin("DmitryIvanov");
        user.setPassword("000");
        user.setAge(30);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Password can't be lower than " + MIN_PASSWORD_LENGTH, actual.getMessage());
    }

    @Test
    void register_passwordTooShort_5char_notOk() {
        User user = new User();
        user.setLogin("AleksIvanov");
        user.setPassword("897ww");
        user.setAge(24);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Password can't be lower than " + MIN_PASSWORD_LENGTH, actual.getMessage());
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setLogin("GrigoriiLebedev");
        user.setPassword(null);
        user.setAge(70);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Password can't be empty or null", actual.getMessage());
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        User user = new User();
        user.setLogin("EvgeniiAzov");
        user.setPassword("");
        user.setAge(45);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Password can't be empty or null", actual.getMessage());
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User();
        user.setLogin("SvetlanaPavlova");
        user.setPassword("123456789");
        user.setAge(null);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Age can't be null", actual.getMessage());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("SvetlanaPavlova");
        user.setPassword("123456789");
        user.setAge(-8);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Age can't be lower than " + MIN_AGE, actual.getMessage());
    }

    @Test
    void register_AgeZero_notOk() {
        User user = new User();
        user.setLogin("edaDGDG");
        user.setPassword("123456789");
        user.setAge(0);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Age can't be lower than " + MIN_AGE, actual.getMessage());
    }

    @Test
    void register_age3TooYoung_notOk() {
        User user = new User();
        user.setLogin("SvetlanaPavlova");
        user.setPassword("123456789");
        user.setAge(3);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Age can't be lower than " + MIN_AGE, actual.getMessage());
    }

    @Test
    void register_age17TooYoung_notOk() {
        User user = new User();
        user.setLogin("PavelSmirnov");
        user.setPassword("32455252");
        user.setAge(17);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user));
        assertEquals("Age can't be lower than " + MIN_AGE, actual.getMessage());
    }

    @Test
    void register_storageContainUsername_notOk() {
        User user = new User();
        user.setLogin("NikitaNikitin");
        user.setPassword("chnrf3f33ASf");
        user.setAge(55);
        Storage.people.add(user);
        User user2 = new User();
        user2.setLogin("NikitaNikitin");
        user2.setPassword("anotherPassword");
        user2.setAge(29);
        RegistrationException actual = assertThrows(
                RegistrationException.class,
                () -> service.register(user2));
        assertEquals("User already exists", actual.getMessage());
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("AliakseiShyhala");
        user.setPassword("validPassword");
        user.setAge(30);
        User registered = service.register(user);
        assertEquals(user.getLogin(), registered.getLogin());
        assertEquals(user.getPassword(), registered.getPassword());
        assertEquals(user.getAge(), registered.getAge());
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_validAge_ok() {
        User user = new User();
        user.setLogin("IliaViktorov");
        user.setPassword("validPassword123");
        user.setAge(18);
        User registered = service.register(user);
        assertEquals(user.getLogin(), registered.getLogin());
        assertEquals(user.getPassword(), registered.getPassword());
        assertEquals(user.getAge(), registered.getAge());
    }

    @Test
    void register_validPassword_ok() {
        User user = new User();
        user.setLogin("Hanniballecter");
        user.setPassword("B@b3n$");
        user.setAge(36);
        User registered = service.register(user);
        assertEquals(user.getLogin(), registered.getLogin());
        assertEquals(user.getPassword(), registered.getPassword());
        assertEquals(user.getAge(), registered.getAge());
    }

    @Test
    void register_validLogin_ok() {
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("validPassword");
        user.setAge(30);
        User registered = service.register(user);
        assertEquals(user.getLogin(), registered.getLogin());
        assertEquals(user.getPassword(), registered.getPassword());
        assertEquals(user.getAge(), registered.getAge());
        assertTrue(Storage.people.contains(user));
    }
}
