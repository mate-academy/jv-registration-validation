package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("loginOne");
        user.setAge(20);
        user.setPassword("werewjdskfj");
    }

    @Test
    public void userValidParameters_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void userAlreadyPresent_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userPasswordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userAgeNotAdult_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userAgeUnbelieveble_notOk() {
        user.setAge(131);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userPassword_notOk() {
        user.setPassword("dskfj");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userLoginWrong_notOk() {
        user.setLogin("12dfss");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userLoginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userLoginSpaces_notOk() {
        user.setLogin("        ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void userPasswordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
