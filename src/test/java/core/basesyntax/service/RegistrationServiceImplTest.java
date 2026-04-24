package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;
    private User validUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User();
        validUser.setLogin("Senior");
        validUser.setPassword("123abc");
        validUser.setAge(38);
    }

    @Test
    void registerValidUser_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void registerShortLogin_NotOk() {
        validUser.setLogin("Short");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerShortPassword_NotOk() {
        validUser.setPassword("12345");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerTooYoung_NotOk() {
        validUser.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void registerUserAlreadyExist_NotOk() {
        validUser.setLogin("alreadyRegisteredUser");
        Storage.people.add(validUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerNullLogin_NotOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerNullPassword_NotOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerNullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerNegativeAge_NotOk() {
        validUser.setAge(-1);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser));
    }

    @Test
    void registerAgeExactlyEighteen_Ok() {
        validUser.setLogin("EighteenYearsOldUser");
        validUser.setAge(18);
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }
}
/*here is no user with such login in the Storage yet
user's login is at least 6 characters
user's password is at least 6 characters
user's age is at least 18 years old*/

