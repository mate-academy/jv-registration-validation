package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User(null, "igor67sych", 22);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsLessThanMinLength_notOk() {
        User user = new User("mary", "mary34785", 77);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsMinLength_ok() {
        User expected = new User("george", "george45", 45);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_loginIsLargerThanMin_ok() {
        User expected = new User("tetianka", "tetiana22", 22);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        User user = new User("valentyna", "valia2134", 56);
        Storage.PEOPLE.add(user);
        User newUser = new User("valentyna", "val45ty78", 34);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_loginIsBlankLine_notOk() {
        User user = new User("", "hgy57ea", 33);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_ok() {
        User expected = new User("kateryna", "katya789", 19);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User("taylor12", null, 24);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsMinLength_ok() {
        User expected = new User("taylor12", "taylor", 24);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_passwordIsEmptyLine_notOk() {
        User user = new User("mariyka56", "", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsInvalid_notOk() {
        User user = new User("mariakot", "mar", 34);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsLessThanEighteen_notOk() {
        User user = new User("alice@gmail.com", "alice348", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNegative_notOk() {
        User user = new User("mariakot", "mariakot56", -7);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
