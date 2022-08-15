package core.basesyntax;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class HelloWorldTest {
    RegistrationService registrationService = new RegistrationServiceImpl();
    @BeforeAll
    static void beforeAll() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(25);
    }

    @Test
    void registerUserIsNull_NotOk() {
        try {
            registrationService.register(null);
        } catch (ValidationException e) {
            return;
        }
        fail("User cannot be null");
    }
}
