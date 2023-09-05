package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_WithNewLogin_Ok() {
        User user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        User user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(19);
        User newUser = new User();
        newUser.setLogin("ValidLogin");
        newUser.setPassword("ValidPassword");
        newUser.setAge(19);
        Storage.PEOPLE.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_LoginMinValidLength_Ok() {
        User user = new User();
        user.setLogin("6chars");
        user.setPassword("ValidPassword");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginMinEdgeValidLength_Ok() {
        User user = new User();
        user.setLogin("7 chars");
        user.setPassword("ValidPassword");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginIsNull_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("ValidPassword");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginIsBlank_NotOk() {
        User user = new User();
        user.setLogin("       ");
        user.setPassword("ValidPassword");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginMaxEdgeInvalidLength_NotOk() {
        User user = new User();
        user.setLogin("5char");
        user.setPassword("ValidPassword");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordMinValidLength_Ok() {
        User user = new User();
        user.setLogin("6chars");
        user.setPassword("Length");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordMinEdgeValidLength_Ok() {
        User user = new User();
        user.setLogin("6chars");
        user.setPassword("7 chars");
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        User user = new User();
        user.setLogin("6chars");
        user.setPassword(null);
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsBlank_NotOk() {
        User user = new User();
        user.setLogin("6chars");
        user.setPassword("       ");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordMaxEdgeInvalidLength_NotOk() {
        User user = new User();
        user.setLogin("6chars");
        user.setPassword("5char");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeMinEdgeValid_Ok() {
        User user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_AgeMaxEdgeInvalid_NotOk() {
        User user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeIsZero_NotOk() {
        User user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeIsNegative_NotOk() {
        User user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
