package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_validUser_Ok() {
        User user = new User("Robert", "qwerty123", 18);
        registrationService.register(user);
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("qwe", "123456", 18);
        assertThrows(RegistrationExseption.class,() -> registrationService
                .register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("qwertyu", "1", 18);
        assertThrows(RegistrationExseption.class,() -> registrationService
                .register(user));
    }

    @Test
    void userWithTheSameLoginInStorage_NotOk() {
        User user = new User("qwerty", "123456", 18);
        registrationService.register(user);
        User existUser = new User("qwerty", "123456", 18);
        assertThrows(RegistrationExseption.class,() -> registrationService.register(existUser));
    }

    @Test
    void checkIsUserAdult_NotOk() {
        User user = new User("qwerty", "123456", 17);
        assertThrows(RegistrationExseption.class,() -> registrationService
                .register(user));
    }
}
