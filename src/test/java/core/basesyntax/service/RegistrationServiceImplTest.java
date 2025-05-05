package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User firstUser;
    private User secondUser;
    private InvalidDataException exception;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        firstUser = new User();
        firstUser.setLogin("andrnorm");
        firstUser.setAge(18);
        firstUser.setPassword("andrik");

        secondUser = new User();
        secondUser.setLogin("andrnorm2");
        secondUser.setAge(20);
        secondUser.setPassword("andrik152");
    }

    @Test
    public void register_existingUser_notOk() {
        Storage.people.add(firstUser);

        secondUser.setLogin("andrnorm");
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(secondUser);
        });

        assertEquals("Such login is already exist", exception.getMessage());
    }

    @Test
    public void register_shortLogin_notOk() {
        firstUser.setLogin("Andr");
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(firstUser);
        });

        assertEquals("Login is too short", exception.getMessage());

        secondUser.setLogin("");
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(secondUser);
        });

        assertEquals("Login is too short", exception.getMessage());
    }

    @Test
    public void register_shortPassword_notOk() {
        firstUser.setPassword("andre");
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(firstUser);
        });

        assertEquals("Password is too short", exception.getMessage());

        secondUser.setPassword("");
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(secondUser);
        });

        assertEquals("Password is too short", exception.getMessage());
    }

    @Test
    public void register_underage_notOk() {
        firstUser.setAge(17);
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(firstUser);
        });

        assertEquals("You are underage", exception.getMessage());

        secondUser.setAge(-1);
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(secondUser);
        });

        assertEquals("You are underage", exception.getMessage());
    }

    @Test
    public void register_nullUser_notOk() {
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });

        assertEquals("User is null", exception.getMessage());
    }

    @Test
    public void register_allOk() {
        firstUser.setLogin("androk");
        User expected = firstUser;
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);

        expected = secondUser;
        actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(firstUser);
        });

        assertEquals("Login is null", exception.getMessage());
    }

    @Test
    public void register_nullPassword_notOk() {
        firstUser.setPassword(null);
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(firstUser);
        });

        assertEquals("Password is null", exception.getMessage());
    }

    @Test
    public void register_nullAge_notOk() {
        firstUser.setAge(null);
        exception = assertThrows(InvalidDataException.class, () -> {
            registrationService.register(firstUser);
        });

        assertEquals("Age is null", exception.getMessage());
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}
