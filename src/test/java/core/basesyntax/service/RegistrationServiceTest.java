package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void validUser_Ok() {
        User test = new User("Mishutka", "123456789", 33);
        User registrationTest = registrationService.register(test);
        assertNotNull(registrationTest);
        assertEquals("Mishutka", registrationTest.getLogin());
    }

    @Test
    void duplicateLogin_NotOK() {
        User user1 = new User("duplicate", "winter2023", 78);
        User user2 = new User("duplicate", "winter2024", 77);
        registrationService.register(user1);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user2));
        assertEquals("The same login already exists", exception.getMessage());
    }

    @Test
    void negativeAge_NotOk() {
        User user = new User("login123", "00000000", -100);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullLogin_NotOk() {
        User user = new User(null, "99887766", 23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void shortLogin_NotOk() {
        User user = new User("one", "1513145", 16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void shortPassword_NotOk() {
        User user = new User("ukrainian", "abc", 46);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        User user = new User("absdefgrk", null, 29);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void ageUnder18_NotOK() {
        User user = new User("natalia", "1234567", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
