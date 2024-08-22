package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("Good login");
        user.setPassword("Good password");
        user.setAge(19);

        Storage.people.add(user);

        User newUser = new User();
        newUser.setLogin("Good login");
        newUser.setPassword("Good password");
        newUser.setAge(19);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser),
                "Expected register() to throw, but it didn't");
    }

    @Test
    public void register_shortLogin_notOK() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("Good password");
        user.setAge(19);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Login is too short");
    }

    @Test
    public void register_shortPassword_notOK() {
        User user = new User();
        user.setLogin("Good login");
        user.setPassword("short");
        user.setAge(19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Password is too short");
    }

    @Test
    public void register_lowerAge_notOK() {
        User user = new User();
        user.setLogin("Good login");
        user.setPassword("Good password");
        user.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Age is too small");
    }

    @Test
    public void register_negativeAge_notOK() {
        User user = new User();
        user.setLogin("Good login");
        user.setPassword("Good password");
        user.setAge(-1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Negative age is not allowed");
    }

    @Test
    public void register_validData_success() {
        User user = new User();
        user.setLogin("Good login");
        user.setPassword("Good password");
        user.setAge(19);

        User actual = registrationService.register(user);

        assertEquals(1, Storage.people.size());
        assertEquals(user, actual);
    }
}

