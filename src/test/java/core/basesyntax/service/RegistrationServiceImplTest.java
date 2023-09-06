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
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reexXx");
        newUser.setPassword("qwerty");
        newUser.setAge(25);
        User registeredUser = registrationService.register(newUser);
        assertEquals(newUser, registeredUser);
    }

    @Test
    void register_userWithShortLogin_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reex");
        newUser.setPassword("qwerty");
        newUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setPassword("qwerty");
        newUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithShortPassword_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reex");
        newUser.setPassword("qwer");
        newUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reex");
        newUser.setPassword(null);
        newUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void regixter_userWithCorrectLengthPassword_ok() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reexXx");
        newUser.setPassword("123456789");
        newUser.setAge(25);
        String expected = "123456789";
        assertEquals(newUser.getPassword(), expected);

    }

    @Test
    void register_userWithInvalidAge_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reex");
        newUser.setPassword("qwerty");
        newUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithNullAge_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reex");
        newUser.setPassword("qwerty");
        newUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithNegativeAge_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reexXx");
        newUser.setPassword("qwerty");
        newUser.setAge(-30);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithValidAge_ok() {
        User user = new User();
        user.setId(111L);
        user.setLogin("reexXx");
        user.setPassword("qwerty");
        user.setAge(25);
        registrationService.register(user);
        int expected = 25;
        assertEquals(Storage.PEOPLE.get(0).getAge(), expected);
    }

    @Test
    void register_alreadyRegisteredUser_notOk() {
        User newUser = new User();
        newUser.setId(111L);
        newUser.setLogin("reexXx");
        newUser.setPassword("qwerty");
        newUser.setAge(25);
        registrationService.register(newUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }
}
