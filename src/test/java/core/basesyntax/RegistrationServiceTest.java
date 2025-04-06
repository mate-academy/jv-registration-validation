package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceTest {

    private RegistrationService registrationService;

   @BeforeEach
    void setUp() {
       registrationService = new RegistrationServiceImpl();
       Storage.people.clear();
   }

   @Test
    void register_valid_ok() {
       assertDoesNotThrow(() -> registrationService.register(new User ("newLogin", "123456", 18)));
   }

    @Test
    void register_loginExist_notOk() {
       User user = new User("newLogin", "123456", 22);
       Storage.people.add(user);
       assertThrows(RegistrationException.class, () -> registrationService.register(new User("newLogin", "123456", 22)));
    }

    @Test
    void register_loginLength_notOk() {
       assertThrows(RegistrationException.class, () -> registrationService.register(new User(null, "123456", 22)));
       assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnn", "123456", 22)));
       assertThrows(RegistrationException.class, () -> registrationService.register(new User("njkil", "123456", 22)));
    }

    @Test
    void register_LoginLength_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth", "123456", 22)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngthnjsad", "123456", 22)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngthdxscCSAFSAfa", "123456", 22)));
    }

    @Test
    void  register_Age_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth", "123456", 18)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth1", "123456", 25)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth2", "123456", 100)));
    }

    @Test
    void  register_Age_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnngth", "123456", null)));
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnngth1", "123456", 17)));
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnngth2", "123456", -1)));
    }

    @Test
    void register_passwordLength_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth", "123456", 18)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth1", "12345678", 22)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth2", "123456sfcasVdaGDSgaGsdvg", 99)));
    }
    @Test
    void register_passwordLength_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnngth", "null", 18)));
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnngth1", "123", 22)));
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("nnngth2", "12345", 99)));
    }
}
