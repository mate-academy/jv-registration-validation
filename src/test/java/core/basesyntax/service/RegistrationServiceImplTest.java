package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private final static int MIN_LENGTH_LOGIN = 6;
    private RegistrationService registration;
    @BeforeEach
    void setUp() {
        registration = new RegistrationServiceImpl();
    }

    @Test
    void add_notFindSuchLogin_ok() {
        User differentLogin1 = new User("differentLogin1", "password", 18);
        User differentLogin2 = new User("differentLogin2", "password", 18);
        User differentLogin3 = new User("differentLogin3", "password", 18);
        Storage.people.add(differentLogin1);
        Storage.people.add(differentLogin2);
        Storage.people.add(differentLogin3);
        assertEquals(Storage.people.size(), Storage.people.size());
    }

    @Test
    void add_findSuchLogin_notOK() {
        User sameLogin1 = new User("sameLogin", "password", 18);
        User differentUser1 = new User("differentLogin1", "password", 18);
        Storage.people.add(sameLogin1);
        Storage.people.add(differentUser1);
        assertThrows(RegistrationException.class, () ->
                Storage.people.add(new User("sameLogin", "password", 18)));
        assertThrows(RegistrationException.class, () ->
                Storage.people.add(new User("differentLogin2", "password", 18)));
    }

    @Test
    void add_loginIsValid_ok() {
        User loginIsValid1 = new User("loginValid", "password", 18);
        User loginIsValid2 = new User("loginValidAlso", "password", 18);
        User loginIsValid3 = new User("AlsoLoginValid", "password", 18);
        User loginIsValid4 = new User("login!", "password", 18);
        User loginNotValid1 = new User("log", "password", 18);
        User loginNotValid2 = new User("not", "password", 18);
        User loginNotValid3 = new User("valid", "password", 18);
        Storage.people.add(loginIsValid1);
        Storage.people.add(loginIsValid2);
        Storage.people.add(loginIsValid3);
        Storage.people.add(loginIsValid4);
        Storage.people.add(loginNotValid2);
        Storage.people.add(loginNotValid3);
        assertTrue(loginIsValid1.getLogin().length() >= MIN_LENGTH_LOGIN);
        assertTrue(loginIsValid2.getLogin().length() >= MIN_LENGTH_LOGIN);
        assertTrue(loginIsValid3.getLogin().length() >= MIN_LENGTH_LOGIN);
        assertTrue(loginIsValid4.getLogin().length() >= MIN_LENGTH_LOGIN);
        assertThrows(RegistrationException.class, () -> Storage.people.add(loginNotValid1));
        assertThrows(RegistrationException.class, () -> Storage.people.add(loginNotValid2));
        assertThrows(RegistrationException.class, () -> Storage.people.add(loginNotValid3));
    }

    @Test
    void add_loginNotValid_notOk() {
        User loginNotValid1 = new User("log", "password", 18);
        User loginNotValid2 = new User("not", "password", 18);
        User loginNotValid3 = new User("valid", "password", 18);
        assertTrue(loginNotValid1.getLogin().length() < MIN_LENGTH_LOGIN);
        assertTrue(loginNotValid2.getLogin().length() < MIN_LENGTH_LOGIN);
        assertTrue(loginNotValid3.getLogin().length() < MIN_LENGTH_LOGIN);

    }

    @Test
    void add_loginNotNull_ok() {
    }

    @Test
    void add_loginIsNull_notOk() {
    }

    @Test
    void add_loginNotEmpty_oK() {
    }

    @Test
    void add_loginIsEmpty_notOk() {
    }

    @Test
    void add_passwordValid_ok() {
    }

    @Test
    void add_passwordNotValid_notOk() {
    }

    @Test
    void add_passwordNotNull_ok() {
    }

    @Test
    void add_passwordIsNull_notOk() {
    }

    @Test
    void add_passwordNotEmpty_ok() {
    }

    @Test
    void add_passwordIsEmpty_notOk() {
    }

    @Test
    void add_ageIsValid_ok() {
    }

    @Test
    void add_ageNotValid_notOK() {
    }

    @Test
    void add_ageNotNull_ok() {
    }

    @Test
    void add_ageIsNull_notOk() {
    }
}