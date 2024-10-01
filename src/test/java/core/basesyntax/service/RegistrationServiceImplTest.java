package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void createReference() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void resetStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userAlreadyExists_shouldThrowException() {
        User user = new User();
        user.setLogin("validUser123");
        user.setPassword("password123");
        user.setAge(20);
        Storage.people.add(user);

        User duplicateUser = new User();
        duplicateUser.setLogin("validUser123");
        duplicateUser.setPassword("password123");
        duplicateUser.setAge(20);

        assertThrows(RegistrationException.class, () ->
                registrationService.register(duplicateUser));
    }

    @Test
    void register_ageAboveMin_shouldAddUserToStorage() throws RegistrationException {
        User user = new User();
        user.setLogin("validUser123");
        user.setPassword("validPassword123");
        user.setAge(20);

        registrationService.register(user);

        assertEquals(1, Storage.people.size());
        assertEquals("validUser123", Storage.people.get(0).getLogin());
    }

    @Test
    void register_passwordLengthAtMin_shouldSucceed() throws RegistrationException {
        User user = new User();
        user.setLogin("userWithPass");
        user.setPassword("pass12");
        user.setAge(20);

        registrationService.register(user);

        assertEquals(1, Storage.people.size());
        assertEquals("userWithPass", Storage.people.get(0).getLogin());
    }

    @Test
    void register_passwordLengthBelowMin_shouldThrowException() {
        User user = new User();
        user.setLogin("userWithShortPass");
        user.setPassword("pass");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_shouldThrowException() {
        User user = new User();
        user.setPassword("validPassword");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_shouldThrowException() {
        User user = new User();
        user.setLogin("validUser123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageBelowMin_shouldThrowException() {
        User user = new User();
        user.setLogin("underageUser");
        user.setPassword("validPassword");
        user.setAge(15);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthAtMin_shouldSucceed() throws RegistrationException {
        User user = new User();
        user.setLogin("abcde1");
        user.setPassword("validPassword123");
        user.setAge(20);

        registrationService.register(user);

        assertEquals(1, Storage.people.size());
        assertEquals("abcde1", Storage.people.get(0).getLogin());
    }

    @Test
    void register_loginLengthBelowMin_shouldThrowException() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("validPassword123");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_shouldThrowException() {
        User user = new User();
        user.setLogin("validUser123");
        user.setPassword("validPassword123");
        user.setAge(0);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_shouldThrowException() {
        User user = new User();
        user.setLogin("validUser123");
        user.setPassword("validPassword123");
        user.setAge(-5);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
