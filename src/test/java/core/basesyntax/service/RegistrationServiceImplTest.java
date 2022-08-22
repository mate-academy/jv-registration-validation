package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationServiceException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_UserWithNullData_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_UserWithNullLogin_NotOk() {
        User userWithNullLogin = new User(null, "password12345678", 20);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_UserWithNegativeAge_NotOk() {
        User userWithNegativeAge = new User("nineLogin@gmail.com", "ninethPassword", -6);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userWithNegativeAge));
    }

    @Test
    void register_UserWithNullAge_NotOk() {
        User userWithNullAge =
                new User("tenthLogin@gmail.com", "tenthPassword", null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userWithNullAge));
    }

    @Test
    void register_ExistedUser_NotOk() {
        User secondUser =
                new User("secondLogin@gamil.com", "secondPassword", 30);
        registrationService.register(secondUser);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(secondUser));

    }

    @Test
    void register_User_Ok() {
        User user =
                new User("superUser@gmail.com", "123457", 27);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);

    }

    @Test
    void register_UserWithShortPassword_NotOk() {
        User newUser =
                new User("fifthLogin@gmail.com", "fifth", 67);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_YoungUser_NotOk() {
        User youngUser =
                new User("sixthLogin@gmail.com", "sixthPassword", 17);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(youngUser));
    }

    @Test
    void register_YoungUserWithShortPassword_NotOk() {
        User youngUserWithShortPassword =
                new User("seventhLogin@gmail.com", "seven", 10);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(youngUserWithShortPassword));
    }
}
