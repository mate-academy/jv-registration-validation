package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    public User getCorrectUser() {
        User correctUser = new User();
        correctUser.setId(1L);
        correctUser.setAge(20);
        correctUser.setPassword("123456");
        correctUser.setLogin("123correctLogin");
        return correctUser;
    }

    @Test
    void register_validUser_ok() {
        User user = getCorrectUser();
        User result = service.register(user);
        assertEquals(user, result);
        assertEquals(1, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_nullAge_notOk() {
        User actual = getCorrectUser();
        actual.setAge(null);
        assertThrows(InvalidDataException.class, () -> 
                service.register(actual));
    }

    @Test
    void register_ageUnder0_notOk() {
        User actual = getCorrectUser();
        actual.setAge(-1);
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_under18_notOk() {
        User actual = getCorrectUser();
        actual.setAge(17);
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_age18_ok() {
        User actual = getCorrectUser();
        actual.setAge(18);
        User result = service.register(actual);
        assertEquals(actual, result);
        assertEquals(1, Storage.people.size());
        assertEquals(actual, Storage.people.get(0));
    }

    @Test
    void register_nullLogin_notOk() {
        User actual = getCorrectUser();
        actual.setLogin(null);
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_emptyLogin_notOk() {
        User actual = getCorrectUser();
        actual.setLogin("");
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_identicalLogin_notOk() {
        User firstLoginUser = getCorrectUser();
        Storage.people.add(firstLoginUser);

        User sameLoginUser = getCorrectUser();
        sameLoginUser.setLogin(firstLoginUser.getLogin());

        assertThrows(InvalidDataException.class, () ->
                service.register(sameLoginUser));
    }

    @Test
    void register_loginLength6_ok() {
        User actual = getCorrectUser();
        actual.setLogin("123456");
        User result = service.register(actual);
        assertEquals(actual, result);
        assertEquals(1, Storage.people.size());
        assertEquals(actual, Storage.people.get(0));
    }

    @Test
    void register_loginLength5_notOk() {
        User actual = getCorrectUser();
        actual.setLogin("12345");
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_loginLengthLessThan6_notOk() {
        User actual = getCorrectUser();
        actual.setLogin("11111");
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_nullPassword_notOk() {
        User actual = getCorrectUser();
        actual.setPassword(null);
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_emptyPassword_notOk() {
        User actual = getCorrectUser();
        actual.setPassword("");
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_passwordLessThan6_notOk() {
        User actual = getCorrectUser();
        actual.setPassword("12");
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_passwordLength5_notOk() {
        User actual = getCorrectUser();
        actual.setPassword("12345");
        assertThrows(InvalidDataException.class, () ->
                service.register(actual));
    }

    @Test
    void register_passwordLength6_ok() {
        User actual = getCorrectUser();
        actual.setPassword("123456");
        User result = service.register(actual);
        assertEquals(actual, result);
        assertEquals(1, Storage.people.size());
        assertEquals(actual, Storage.people.get(0));
    }
}
