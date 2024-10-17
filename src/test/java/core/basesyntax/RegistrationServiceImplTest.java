package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    @Test
    void checkIfUserIsNull_NotOk() {
        User user = new User();
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });

    }
}
