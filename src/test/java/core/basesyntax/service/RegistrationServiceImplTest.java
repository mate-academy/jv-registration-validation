package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_addExistingUser_notOk() {
        User user1 = new User();
        user1.setAge(23);
        user1.setLogin("Chandra");
        user1.setPassword("fjvn84n(nfn");
        User registredUser = registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registredUser);
        });
    }

    @Test
    void register_nullValue_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_ageUnder18_notOk() {
        User user1 = new User();
        user1.setAge(17);
        user1.setLogin("Chandra");
        user1.setPassword("fjvn84n(nfn");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_passwordUnder6_notOk() {
        User user1 = new User();
        user1.setAge(19);
        user1.setLogin("Sandra");
        user1.setPassword("f(R4n");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_validUser_ok() {
        User user1 = new User();
        user1.setAge(23);
        user1.setLogin("Sandra");
        user1.setPassword("fjvn84n(nfn");
        assertNotNull(registrationService.register(user1));
    }
}
