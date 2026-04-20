package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {

    private static final String VALID_LOGIN = "validUser";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 20;

    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);

        User result = service.register(user);

        assertNotNull(result);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }
}
