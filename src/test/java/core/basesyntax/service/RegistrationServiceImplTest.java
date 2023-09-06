package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.WrongValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User("ElonMusk99", "eLONmUSK((", 24);
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearDataBase() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_validUser_Ok() {
        User registratedUser = registrationService.register(newUser);
        assertEquals(1, Storage.PEOPLE.size());
        assertEquals(newUser, registratedUser);
    }

    @Test
    void register_NullUser_NotOk() {
        newUser = null;
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ExistingUser_NotOk() {
        Storage.PEOPLE.add(newUser);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        newUser.setLogin(null);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortLogin_NotOk() {
        newUser.setLogin("Musk2");
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        newUser.setPassword(null);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortPassword_NotOk() {
        newUser.setPassword("qw12");
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerNullAge_NotOk() {
        newUser.setAge(null);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerNegativeAge_NotOk() {
        newUser.setAge(-23);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerSmallAge_NotOk() {
        newUser.setAge(17);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }
}
