package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private User user = new User();
    private RegistrationServiceImpl service = new RegistrationServiceImpl();

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public RegistrationServiceImpl getService() {
        return service;
    }
    public void setService(RegistrationServiceImpl service) {
        this.service = service;
    }

    @BeforeEach
    void prepareUser() {
        user.setAge(18);
        user.setLogin("123456");
        user.setPassword("abcdef");
        Storage.people.clear();
    }

    @Test
    void register_login5Characters_NotOK() {
        user.setLogin("12345");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_login2Characters_NotOK() {
        user.setLogin("12");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_login0Characters_NotOK() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_loginNull_NotOK() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_login6Characters_OK() {
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_login8Characters_OK() {
        user.setLogin("12345678");
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_login30Characters_OK() {
        user.setLogin("012345678901234567890123456789");
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_password5Characters_NotOK() {
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_password2Characters_NotOK() {
        user.setPassword("12");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_password0Characters_NotOK() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_passwordNull_NotOK() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_password6Characters_OK() {
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_password8Characters_OK() {
        user.setPassword("12345678");
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_password30Characters_OK() {
        user.setPassword("012345678901234567890123456789");
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_ageNull_NotOK() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_ageMinus100_NotOK() {
        user.setAge(-100);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_ageMinus1_NotOK() {
        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_age9_NotOK() {
        user.setAge(9);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_age17_NotOK() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_age18_OK() {
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_age19_OK() {
        user.setAge(19);
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_age33_OK() {
        user.setAge(33);
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_age121_OK() {
        user.setAge(121);
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_age122_OK() {
        user.setAge(122);
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_age123_NotOK() {
        user.setAge(123);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_age600_NotOK() {
        user.setAge(600);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_uniqueLogin_OK() {
        assertDoesNotThrow(() -> service.register(user));
    }

    @Test
    void register_returnedCorrectUser_OK() {
        User expected = user;
        User actual = service.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_repetetiveLogin_NotOK() {
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }
}
