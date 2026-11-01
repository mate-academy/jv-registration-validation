package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin("maxym123");
        user.setPassword("maxym123");
        user.setAge(18);
    }

    @Test
    void register_withSameLogin_notOk() {
        registrationService.register(user);

        User userTwo = new User();
        userTwo.setLogin("maxym123");
        userTwo.setPassword("maxym123");
        userTwo.setAge(18);

        assertThrows(RegistrationException.class, () ->
                registrationService.register(userTwo));
    }

    @Test
    void register_correctRegistration_ok() {
        User createdUser = registrationService.register(user);
        assertEquals("maxym123", createdUser.getLogin());
        assertEquals("maxym123", createdUser.getPassword());
        assertEquals(18, createdUser.getAge());
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_loginIsLessThan6_notOk() {
        user.setLogin("maxym");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordIsLessThan6_notOk() {
        user.setPassword("maxym");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_loginIsCorrect_ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_passwordIsCorrect_ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ageIsCorrect_ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_ageIsEighten_ok() {
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ageIsGreater_ok() {
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ageIsLessThanZero_notOk() {
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginThreeChars_notOk() {
        user.setLogin("max");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginZeroChars_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginFiveChars_notOk() {
        user.setLogin("maxym");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordThreeChars_notOk() {
        user.setPassword("max");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordZeroChars_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordFiveChars_notOk() {
        user.setPassword("maxym");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
