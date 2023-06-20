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
    private static User actualUser;
    private static User expectedUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.remove(actualUser);
    }

    @BeforeEach
    void setUp() {
        actualUser = new User();
        expectedUser = new User();
        actualUser.setLogin("MyName");
        actualUser.setPassword("MyPass");
        actualUser.setAge(18);
        expectedUser = actualUser;
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validLogin_Ok() {
        actualUser.setLogin("MyName");
        actualUser = registrationService.register(actualUser);
        expectedUser.setLogin("MyName");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_nullLogin_notOk() {
        actualUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
        actualUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
        actualUser.setLogin("Abcde");
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_notValidLogin_notOk() {
        actualUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
        actualUser.setLogin("Abcde");
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_validPassword_Ok() {
        actualUser.setPassword("qwerty");
        actualUser = registrationService.register(actualUser);
        expectedUser.setPassword("qwerty");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_nullPassword_notOk() {
        actualUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_notValidPassword_notOk() {
        actualUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
        actualUser.setPassword("qwert");
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_nullAge_notOk() {
        actualUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_validAge_Ok() {
        actualUser.setAge(18);
        expectedUser.setAge(18);
        actualUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_notValidAge_notOk() {
        actualUser.setAge(17);
        expectedUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
        actualUser.setAge(-1);
        expectedUser.setAge(-1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_validUser_Ok() {
        actualUser = registrationService.register(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_dublicatedUser_notOk() {
        Storage.people.add(actualUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actualUser));
    }
}
