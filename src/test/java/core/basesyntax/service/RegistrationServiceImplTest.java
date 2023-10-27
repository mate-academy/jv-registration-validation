package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.IllegalUserDataExeption;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User validUser;

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin("ValidLogin");
        validUser.setPassword("ValidPassword");
        validUser.setAge(20);
        validUser.setId(1L);
    }

    @Test
    void register_validUser_Ok() {
        User registredUser = registrationService.register(validUser);
        assertEquals(validUser, registredUser);
    }

    @Test
    void register_notValidLogin_notOk() {
        validUser.setLogin("short");
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidPassword_notOk() {
        validUser.setPassword("short");
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void resister_notValidAge_notOk() {
        validUser.setAge(17);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_duplicateLogin_notOk() {
        registrationService.register(validUser);
        assertThrows(IllegalUserDataExeption.class, () -> registrationService.register(validUser));
    }
}
