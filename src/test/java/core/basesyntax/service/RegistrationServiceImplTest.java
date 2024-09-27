package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setAge(-5);
        user.setLogin("validLogin");
        user.setPassword("validePasssword");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageEquals18_ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("valLog");
        user.setPassword("valPas");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_LoginExist_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registre_LoginLength_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("log");
        user.setPassword("validPass");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginLengthEdgeCase_notOk() {
        User user = new User();
        user.setAge(18);
        user.setLogin("valid");
        user.setPassword("validP");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ValidUser_ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("validL");
        user.setPassword("validP");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_PasswordLength_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("passp");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLength_Ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("validLogin");
        user.setPassword("validP");
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_PasswordLengthEdgeCase_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("valid");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginNull_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin(null);
        user.setPassword("validPass");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordNull_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
