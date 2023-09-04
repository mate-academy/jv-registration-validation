package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @BeforeEach
    void setUp() {
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(19);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_WithNewLogin_Ok() {
        User newUser = new User();
        newUser.setLogin("NewValidLogin");
        newUser.setPassword("ValidPassword");
        newUser.setAge(19);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void register_LoginAlreadyExists_NotOk() {
        User newUser = new User();
        newUser.setLogin("ValidLogin");
        newUser.setPassword("ValidPassword");
        newUser.setAge(19);
        Storage.PEOPLE.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_LoginMinValidLength_Ok() {
        user.setLogin("6chars");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginMinEdgeValidLength_Ok() {
        user.setLogin("7 chars");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginIsBlank_NotOk() {
        user.setLogin("        ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginMaxEdgeInvalidLength_NotOk() {
        user.setLogin("5char");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordValidLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordMinValidLength_Ok() {
        user.setPassword("Length");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordMinEdgeValidLength_Ok() {
        user.setPassword("7 chars");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsBlank_NotOk() {
        user.setPassword("       ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordMaxEdgeInvalidLength_NotOk() {
        user.setPassword("5char");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeValid_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_AgeMinEdgeValid_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_AgeMaxEdgeInvalid_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeIsZero_NotOk() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeIsNegative_NotOk() {
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
