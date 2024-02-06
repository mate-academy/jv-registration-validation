package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ExpectedException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY = "";
    private static final String THREE_CHARACTERS = "abc";
    private static final String FIVE_CHARACTERS = "abcdf";
    private static final String SIX_CHARACTERS = "abcdef";
    private static final String EIGHT_CHARACTERS = "abcdefas";
    private static RegistrationService registrationService;
    private static User user;
    private static User newUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        newUser = new User();
    }

    @BeforeEach
    void setUp() {
        user.setAge(20);
        user.setLogin("userLogin");
        user.setPassword("12345678");

        newUser.setAge(32);
        newUser.setLogin("userNewLogin");
        newUser.setPassword("12345678New");
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_0_Age_notOk() {
        user.setAge(0);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_9_Age_notOk() {
        user.setAge(9);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_17_Age_notOk() {
        user.setAge(17);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_0_passwordLength_notOk() {
        user.setPassword(EMPTY);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_3_passwordLength_notOk() {
        user.setPassword(THREE_CHARACTERS);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_5_passwordLength_notOk() {
        user.setPassword(FIVE_CHARACTERS);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_0_loginLength_notOk() {
        user.setLogin(EMPTY);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_3_loginLength_notOk() {
        user.setLogin(THREE_CHARACTERS);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_5_loginLength_notOk() {
        user.setLogin(FIVE_CHARACTERS);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_6_loginLength_Ok() {
        user.setLogin(SIX_CHARACTERS);
        String expected = user.getLogin();
        User userActual = registrationService.register(user);
        String actual = userActual.getLogin();
        assertEquals(expected, actual, "user's login is at least 6 characters");
    }

    @Test
    void register_8_loginLength_Ok() {
        user.setLogin(EIGHT_CHARACTERS);
        String expected = user.getLogin();
        User userActual = registrationService.register(user);
        String actual = userActual.getLogin();
        assertEquals(expected, actual, "user's login is at least 6 characters");
    }

    @Test
    void register_6_passwordLength_Ok() {
        user.setLogin(SIX_CHARACTERS);
        String expected = user.getPassword();
        User userActual = registrationService.register(user);
        String actual = userActual.getPassword();
        assertEquals(expected, actual, "user's password is at least 6 characters");
    }

    @Test
    void register_8_passwordLength_Ok() {
        user.setLogin(EIGHT_CHARACTERS);
        String expected = user.getPassword();
        User userActual = registrationService.register(user);
        String actual = userActual.getPassword();
        assertEquals(expected, actual, "user's password is at least 6 characters");
    }

    @Test
    void register_addExistUser_notOk() {
        User cloneUser = user;
        registrationService.register(user);
        assertThrows(ExpectedException.class, () -> {
            registrationService.register(cloneUser);
        });
    }

    @Test
    void register_addTwoUsers_Ok() {
        registrationService.register(user);
        registrationService.register(newUser);
        int expected = 2;
        int actual = Storage.people.size();
        assertEquals(expected, actual, "storage size should be two");
    }

    @Test
    void register_returnAddedUser_Ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual, "return users must be equals added user");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
