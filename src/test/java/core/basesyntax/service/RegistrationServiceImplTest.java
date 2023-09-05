package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_validUser_ok() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reexXx");
        actual.setPassword("qwerty");
        actual.setAge(25);
        User registeredUser = registrationService.register(actual);
        assertEquals(actual, registeredUser);
    }

    @Test
    void register_userWithShortLogin_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reex");
        actual.setPassword("qwerty");
        actual.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setPassword("qwerty");
        actual.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_userWithShortPassword_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reex");
        actual.setPassword("qwer");
        actual.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reex");
        actual.setPassword(null);
        actual.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void regixter_userWithCorrectLengthPassword_ok() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reexXx");
        actual.setPassword("123456789");
        actual.setAge(25);
        String expected = "123456789";
        assertEquals(actual.getPassword(), expected);

    }

    @Test
    void register_userWithInvalidAge_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reex");
        actual.setPassword("qwerty");
        actual.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_userWithNullAge_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reex");
        actual.setPassword("qwerty");
        actual.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_userWithNegativeAge_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reexXx");
        actual.setPassword("qwerty");
        actual.setAge(-30);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_userWithValidAge() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reexXx");
        actual.setPassword("qwerty");
        actual.setAge(25);
        int expected = 25;
        assertEquals(actual.getAge(), expected);
    }

    @Test
    void register_alreadyRegisteredUser_notOk() {
        User actual = new User();
        actual.setId(111L);
        actual.setLogin("reexXx");
        actual.setPassword("qwerty");
        actual.setAge(25);
        registrationService.register(actual);
        assertThrows(InvalidDataException.class, () -> registrationService.register(actual));
    }
}
