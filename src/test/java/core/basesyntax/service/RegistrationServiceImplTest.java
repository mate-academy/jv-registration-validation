package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataOfUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @Test
    void addNullUser() {
        assertThrows(InvalidDataOfUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(17);
        user.setLogin("1234567");
        user.setPassword("1234567");
        assertThrows(InvalidDataOfUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("12345");
        user.setPassword("1234553");
        user.setAge(18);
        assertThrows(InvalidDataOfUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setLogin("123435");
        user.setPassword("1234");
        user.setAge(18);
        assertThrows(InvalidDataOfUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User existUser = new User();
        existUser.setLogin("123456");
        existUser.setPassword("123456");
        existUser.setAge(18);
        registrationService.register(existUser);

        user.setLogin("123456");
        user.setPassword("1266634");
        user.setAge(18);
        assertThrows(InvalidDataOfUserException.class, () -> registrationService.register(user));
    }

}
