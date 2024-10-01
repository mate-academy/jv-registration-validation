package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPass";
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_AgeNull_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setAge(-5);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginExist_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(20);
        Storage.people.add(user);
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(22);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void registre_LoginLengthShort_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("log");
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginLengthEdgeCase_notOk() {
        User user = new User();
        user.setAge(18);
        user.setLogin("valid");
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ValidUser_ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_PasswordLengthShort_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin(VALID_LOGIN);
        user.setPassword("passp");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLengthEdgeCase_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin(VALID_LOGIN);
        user.setPassword("valid");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LoginNull_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordNull_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
