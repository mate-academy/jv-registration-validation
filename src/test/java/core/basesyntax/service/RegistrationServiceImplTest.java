package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public User getDefaultValidUser() {
        User user = new User();
        user.setLogin("validLogin");
        user.setId(38628L);
        user.setPassword("unuwjf8j2");
        user.setAge(22);
        return user;
    }

    @Test
    void registrationDataIsValid() throws InvalidDataException {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        try {
            registrationService.register(user);
        } catch (InvalidDataException e) {
            throw new InvalidDataException("Oops, your input data is not valid");
        }
    }

    @Test
    void passwordIsValid() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        int expected = 6;
        assertTrue(user.getPassword().length() >= expected);
    }

    @Test
    void loginIsValid() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        int expected = 6;
        assertTrue(user.getLogin().length() >= expected);
    }

    @Test
    void ageIsOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        int expected = 18;
        assertTrue(user.getAge() >= expected);
    }

    @Test
    void ageIsNotOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        user.setAge(16);
        int expected = 18;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNotValid() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        user.setPassword("321");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void inputDataIsNull() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        user.setId(null);
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
