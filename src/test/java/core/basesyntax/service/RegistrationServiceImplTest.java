package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.DataExceptions;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    @Test
    void register_loginIsUsed_NotOk() {
        User userOne = new User(1234L, "johnytest", "123456", 19);
        User userTwo = new User(4321L, "johnytest", "654321", 20);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

        try {
            registrationService.register(userOne);
        } catch (DataExceptions e) {
            throw new RuntimeException(e);
        }

        assertThrows(DataExceptions.class, () -> registrationService.register(userTwo));
    }

    @Test
    void register_loginLength_NotOk() {
        User user = new User(1234L, "bob", "123456", 19);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

        assertThrows(DataExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_NotOk() {
        User user = new User(1234L, "bobtest", "123", 19);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

        assertThrows(DataExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_adult_NotOk() {
        User user = new User(1234L, "bobtest", "123456", 12);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

        assertThrows(DataExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullValue_NotOk() {
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

        User userOne = new User(null, "bobone", "123456", 18);
        assertThrows(DataExceptions.class, () -> registrationService.register(userOne));

        User userTwo = new User(1234L, null, "123456", 18);
        assertThrows(DataExceptions.class, () -> registrationService.register(userTwo));

        User userThree = new User(2345L, "bobthree", null, 18);
        assertThrows(DataExceptions.class, () -> registrationService.register(userThree));

        User userFour = new User(3456L, "bobfour", "123456", null);
        assertThrows(DataExceptions.class, () -> registrationService.register(userFour));

    }

}


