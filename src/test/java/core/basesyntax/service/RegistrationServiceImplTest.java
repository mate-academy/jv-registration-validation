package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User currentUser;
    private String loginNotExist;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        loginNotExist = "login12345678";
    }

    @Test
    void register_nullUser_notOK() {
        currentUser = null;
        assertThrows(ValidationException.class, () -> registrationService.register(currentUser));
    }

    @Test
    void register_nullLogin_notOK() {
        currentUser = new User(0L,null,"password",19);
        assertThrows(ValidationException.class,
                () -> registrationService.register(currentUser));
    }

    @Test
    void register_login5Char_notOK() {
        currentUser = new User(0L,"login","password",19);
        assertThrows(ValidationException.class,
                () -> registrationService.register(currentUser));
    }

    @Test
    void register_login6Char_isOK() {
        currentUser = new User(0L,"login6","password",19);
        assertEquals(currentUser, registrationService.register((currentUser)),
                "Current USER must be returned");
        Storage.people.remove(currentUser);
    }

    @Test
    void register_login7Char_isOK() {
        currentUser = new User(0L,"login77","password",19);
        assertEquals(currentUser, registrationService.register((currentUser)),
                "Current USER must be returned");
        Storage.people.remove(currentUser);
    }

    @Test
    void register_nullPassword_notOK() {
        currentUser = new User(0L,"login1",null,19);
        assertThrows(ValidationException.class,
                () -> registrationService.register(currentUser));
    }

    @Test
    void register_password5Char_notOK() {
        currentUser = new User(0L,"login2","passw",19);
        assertThrows(ValidationException.class,
                () -> registrationService.register(currentUser));
    }

    @Test
    void register_password6Char_isOK() {
        currentUser = new User(0L,"login3","passwo",19);
        assertEquals(currentUser, registrationService.register((currentUser)),
                "Current USER must be returned");
        Storage.people.remove(currentUser);
    }

    @Test
    void register_password7Char_isOK() {
        currentUser = new User(0L,"login4","passwor",19);
        assertEquals(currentUser, registrationService.register((currentUser)),
                "Current USER must be returned");
        Storage.people.remove(currentUser);
    }

    @Test
    void register_nullAge_notOK() {
        currentUser = new User(0L,"login5","password",null);
        assertThrows(ValidationException.class,
                () -> registrationService.register(currentUser));
    }

    @Test
    void register_age17_notOK() {
        currentUser = new User(0L,"login6","password",17);
        assertThrows(ValidationException.class,
                () -> registrationService.register(currentUser));
    }

    @Test
    void register_age18_isOK() {
        currentUser = new User(0L,"login7","password",18);
        assertEquals(currentUser, registrationService.register((currentUser)),
                "Current USER must be returned");
        Storage.people.remove(currentUser);
    }

    @Test
    void register_age19_isOK() {
        currentUser = new User(0L,"login8","password",19);
        assertEquals(currentUser, registrationService.register((currentUser)),
                "Current USER must be returned");
        Storage.people.remove(currentUser);
    }

    @Test
    void register_sameLogin_notOK() {
        User user9 = new User(0L,"login9","password1",18);
        User user10 = new User(0L,"login10","password2",19);
        User user11 = new User(0L,"login11","password3",20);
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user9);
        userList.add(user10);
        userList.add(user11);
        Storage.people.addAll(0, userList);
        assertThrows(ValidationException.class,
                () -> registrationService.register(new User(0L,"login9","password1",18)));
        assertThrows(ValidationException.class,
                () -> registrationService.register(new User(0L,"login11","password3",20)));
        Storage.people.removeAll(userList);
    }
}
