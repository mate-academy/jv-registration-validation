package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(20);

        service.register(user);

        assertNotNull(Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }
}
