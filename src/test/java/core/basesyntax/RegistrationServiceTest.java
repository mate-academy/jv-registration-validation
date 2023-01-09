package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.LoginExistException;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
        registrationService.register(new User("boblogin", "bobpassword", 18));
        registrationService.register(new User("alicelogin", "alicepassword", 25));
        registrationService.register(new User("denislogin", "denispassword",45));
    }

    @Test
    void checkLoginEmpty_NotOk() {
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(new User(null,"iortrion",24)));
    }

    @Test
    void checkEmptyUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void checkPasswordIsEmpty_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(new User("denis", null, 43)));
    }

    @Test
    void checkAgeIsEmpty_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("denis","iortrion",null));
        });
    }

    @Test
    void registrationCompleteForThreeUsers_Ok() {
        List actual = Storage.people;
        assertEquals(3, actual.size());
        assertTrue(actual.contains(new User("boblogin", "bobpassword", 18)));
        assertTrue(actual.contains(new User("alicelogin", "alicepassword", 25)));
        assertTrue(actual.contains(new User("denislogin", "denispassword", 45)));
    }

    @Test
    void passwordLessThanSixSigns_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("denislogin6549", "denis", 25));
        });
    }

    @Test
    void loginExist_false() {
        assertThrows(LoginExistException.class, () -> {
            registrationService.register(new User("denislogin", "denispassword22",25));
        });
    }

    @Test
    void incorrectAge_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("denis1987", "oirudpwuuo091", 325));
        });

    }
}
