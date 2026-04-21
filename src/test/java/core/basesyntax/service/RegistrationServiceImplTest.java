package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService service;
    private User user;

    @BeforeEach
    public void setUp() {
        service = new RegistrationServiceImpl();
        user = new User();
    }

    @Test
    public void userIsNull_notOk() {
        assertThrows(CustomException.class, () -> service.register(null));
    }

    @Test
    public void ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void ageIsLessThenExpected_NotOk() {
        user.setAge(16);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void passwordLengthIsLessThenExpected_NotOk() {
        user.setPassword("34gd");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void loginLengthIsLessThenExpected_NotOk() {
        user.setLogin("Bob");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void userAlreadyExistInDb_NotOk() {
        user.setAge(18);
        user.setLogin("Bobby123");
        user.setPassword("123456");

        try {
            service.register(user);
        } catch (CustomException e) {

        }

        User user2 = new User();
        user2.setAge(18);
        user2.setLogin("Bobby123");
        user2.setPassword("1234563");

        assertThrows(CustomException.class, () -> service.register(user2));
    }
}