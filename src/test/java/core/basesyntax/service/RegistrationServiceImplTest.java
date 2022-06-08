package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("loginOne");
        user.setAge(20);
        user.setPassword("werewjdskfj");
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void userValidParameters_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void userAlreadyPresent_NotOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNotAdult_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeUnbelieveble_NotOk() {
        user.setAge(131);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPassword_NotOk() {
        user.setPassword("dskfj");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginWrong_NotOk() {
        user.setLogin("12dfss");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginSpaces_NotOk() {
        user.setLogin("        ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
