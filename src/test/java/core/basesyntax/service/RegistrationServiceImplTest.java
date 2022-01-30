package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void registerUserNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerIdNull_notOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(null, "john34", "password", 18));
        });
    }

    @Test
    void registerLoginNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(1345L, null, "password", 18));
        });
    }

    @Test
    void registerPasswordNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(1345L, "Lol01", null, 18));
        });
    }

    @Test
    void registerNullAge_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(1345L, "Sam01", "password", null));
        });
    }

    @Test
    void registerSameLogin_notOk() {
        assertThrows(RuntimeException.class, () -> {
            User john01 = registrationService.register(new User(1213L, "john01", "password", 19));
            User john02 = registrationService.register(
                    new User(124415L, "john01", "password02", 26));
            assertEquals(john01.getLogin(), john02.getLogin());
        });
    }

    @Test
    void registerAgeLessThenEighteen_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(134443L, "john34", "password", 15));
        });
    }

    @Test
    void registerAgeNotEqualZero_Ok() {
        assertThrows(RuntimeException.class, () -> {
            User user = registrationService.register(new User(134443L, "john34", "password", 0));
            assertEquals(0, (int) user.getAge());
        });
    }

    @Test
    void registerAgeNegative_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(134443L, "john34", "password", -2));
        });
    }

    @Test
    void registerLoginLengthLessThenSix_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(134443L, "bobo", "password", 44));
        });
    }

    @Test
    void registerPasswordLengthLessThenSix_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(134443L, "login0101", "pass", 44));
        });
    }

    @Test
    void registerFirstLetterOfLogin() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(134443L, "$ogin0101", "pass", 44));
        });
    }

    @Test
    void registerPasswordNotContainsADigits_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(134443L, "login0101", "password", 44));
        });
    }
}
