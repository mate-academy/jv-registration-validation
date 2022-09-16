package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeEach
    void beforeEach() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void userCannotBeNull_NotOK() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void isUserHasNullParameter_NotOK() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(
                        new User("without_age@gmail.com", "password", null)
                ));
    }

    @Test
    void userIsAlreadyExists_NotOK() {
        registrationService.register(new User("bob@gmail.com", "password", 23));
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User("bob@gmail.com", "bob2003", 19)));
    }

    @Test
    void userIsTooYoung_NotOK() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User("too_young@gmail.com", "password", 15)));
    }

    @Test
    void passwordIsTooShort_NotOK() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User("alice@gmail.com", "123", 19)));
    }

    @Test
    void emailSymbolMustBePresent_NotOK() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User("incorrect_login2", "password", 24)));
    }

    @Test
    void emailIsNotCorrect_NotOK() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(new User("incorrect_login1@", "password", 28)));
    }

    @Test
    void correctUserValues_OK() {
        User user = new User("correct@gmail.com", "password", 28);
        assertEquals(registrationService.register(user), user);
    }
}
