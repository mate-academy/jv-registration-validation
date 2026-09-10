package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
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

    private User getCorrectUser() {
        User user = new User();
        user.setAge(20);
        user.setPassword("123456");
        user.setLogin("validLogin");
        return user;
    }

    @Test
    void register_validUser_ok() {
        User user = getCorrectUser();
        User result = service.register(user);
        assertEquals(user, result);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = getCorrectUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = getCorrectUser();
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = getCorrectUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = getCorrectUser();
        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = getCorrectUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = getCorrectUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        service.register(getCorrectUser());
        assertThrows(RegistrationException.class, () -> service.register(getCorrectUser()));
    }
}
