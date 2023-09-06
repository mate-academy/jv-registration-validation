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
    void registerNullUser() {
        newUser = null;
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerExistingUser() {
        Storage.PEOPLE.add(newUser);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        newUser.setLogin(null);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortLogin_notOk() {
        newUser.setLogin("Musk2");
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        newUser.setPassword(null);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortPassword_notOk() {
        newUser.setPassword("qw12");
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerNullAge_notOk() {
        newUser.setAge(null);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerNegativeAge_notOk() {
        newUser.setAge(-23);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registerSmallAge_notOk() {
        newUser.setAge(17);
        assertThrows(WrongValidationException.class, () -> registrationService.register(newUser));
    }
}
