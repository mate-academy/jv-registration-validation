package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_valid_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("newLogin", "123456", 18)));
    }

    @Test
    void register_loginExist_notOk() {
        User user = new User("newLogin", "123456", 22);
        Storage.people.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("newLogin", "123456", 22)));
    }

    @Test
    void register_loginLength_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("", "123456", 22)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnn", "123456", 22)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("njkil", "123456", 22)));
    }

    @Test
    void register_LoginLength_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth", "123456", 22)));
        assertDoesNotThrow(() -> registrationService.register(
                new User("nnngthnjsad", "123456", 22)));
        assertDoesNotThrow(() -> registrationService.register(
                new User("nnngthdxscCSAFSAfa", "123456", 22)));
    }

    @Test
    void register_Age_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth", "123456", 18)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth1", "123456", 25)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth2", "123456", 100)));
    }

    @Test
    void register_Age_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth", "123456", 3)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth1", "123456", 17)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth2", "123456", -1)));
    }

    @Test
    void register_passwordLength_ok() {
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth", "123456", 18)));
        assertDoesNotThrow(() -> registrationService.register(new User("nnngth1", "12345678", 22)));
        assertDoesNotThrow(() -> registrationService.register(
                new User("nnngth2", "123456sfcasVdaGDSgaGsdvg", 99)));
    }

    @Test
    void register_passwordLength_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth", "", 18)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth1", "123", 22)));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth2", "12345", 99)));
    }

    @Test
    void register_passwordNull_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth", null, 18)));
    }

    @Test
    void register_AgedNull_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("nnngth", "1234567", null)));
    }

    @Test
    void register_loginNull_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(null, "1234567890", 18)));
    }

}
