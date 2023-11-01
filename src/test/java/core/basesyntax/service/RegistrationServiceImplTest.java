package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.customexception.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User defaultUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        defaultUser = new User();
        defaultUser.setLogin("validLogin");
        defaultUser.setPassword("validPassword");
        defaultUser.setAge(18);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    public void register_notExistingLogin_ok() {
        User userWithNotExistingLogin = new User();
        userWithNotExistingLogin.setLogin("newLogin");
        userWithNotExistingLogin.setPassword("newpassword");
        userWithNotExistingLogin.setAge(18);
        assertEquals(userWithNotExistingLogin,
                registrationService.register(userWithNotExistingLogin));
    }

    @Test
    public void register_existingLogin_notOk() {
        Storage.people.add(defaultUser);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_lessMinLengthLogin_notOk() {
        defaultUser.setLogin("");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setLogin(" ");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setLogin("login");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setLogin("L");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_validLogin_ok() {
        assertEquals(defaultUser, registrationService.register(defaultUser));
        User newUser = new User();
        newUser.setLogin("anotherUniqValidLogin");
        newUser.setAge(18);
        newUser.setPassword("password1234");
        assertEquals(newUser, registrationService.register(newUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_lessMinLengthPassword_notOk() {
        defaultUser.setPassword("");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setPassword(" ");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setPassword("passw");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setPassword("P");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_validPassword_ok() {
        assertEquals(defaultUser, registrationService.register(defaultUser));
        User newUser = new User();
        newUser.setLogin("loginlogin");
        newUser.setAge(18);
        newUser.setPassword("anotherlengthpassword");
        assertEquals(newUser, registrationService.register(newUser));
    }

    @Test
    public void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_lessMinLengthAge_notOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setAge(0);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));

        defaultUser.setAge(-1);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_validAge_ok() {
        assertEquals(defaultUser, registrationService.register(defaultUser));
        User newUser = new User();
        newUser.setLogin("loginlogin");
        newUser.setAge(50);
        newUser.setPassword("password");
        assertEquals(newUser, registrationService.register(newUser));
    }

}
