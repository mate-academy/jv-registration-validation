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

}


