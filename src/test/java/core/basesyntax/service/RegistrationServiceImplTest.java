package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void ageIsNotOk() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        user.setAge(16);
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
    void loginIsNotValid() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        user.setLogin("jfi");
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

    @Test
    void userAlreadyExist() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = getDefaultValidUser();
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
