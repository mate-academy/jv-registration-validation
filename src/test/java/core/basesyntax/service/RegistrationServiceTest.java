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
    void register_nullUser_notOK() {
        User nullUser = new User();
        assertNotNull(RegistrationException.class, () ->
                String.valueOf(registrationService.register(nullUser)));
    }

    @Test
    void register_validUser_Ok() {
        User test = new User("Mishutka", "123456789", 33);
        User registrationTest = registrationService.register(test);
        assertNotNull(registrationTest);
        assertEquals("Mishutka", registrationTest.getLogin());
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User("duplicate", "winter2023", 78);
        User user2 = new User("duplicate", "winter2024", 77);
        registrationService.register(user1);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user2));
        assertEquals("The same login already exists", exception.getMessage());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User("login123", "00000000", -100);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "99887766", 23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("one", "1513145", 16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("ukrainian", "abc", 46);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("absdefgrk", null, 29);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User("natalia", "1234567", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
