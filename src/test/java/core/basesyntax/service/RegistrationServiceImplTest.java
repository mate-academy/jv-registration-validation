package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
}
