package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        user.setPassword("okayPassword");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        user.setLogin("okayLogin");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void negativeAge_NotOk() {
        user.setLogin("okayLogin");
        user.setPassword("okayPassword");
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void nullAge_NotOk() {
        user.setLogin("okayLogin");
        user.setPassword("okayPassword");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void userExists_NotOk() {
        User otherUser = new User();
        otherUser.setLogin("okayLogin");
        otherUser.setPassword("okayPassword");
        otherUser.setAge(20);
        Storage.people.add(otherUser);
        user.setLogin("okayLogin");
        user.setPassword("okayPassword");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void ageLessThan18_NotOk() {
        user.setLogin("okayLogin");
        user.setPassword("okayPassword");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void shortLogin_NotOk() {
        user.setLogin("login");
        user.setPassword("okayPassword");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void shortPassword_NotOk() {
        user.setLogin("okayLogin");
        user.setPassword("pass");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void validUser_Ok() {
        user.setLogin("j.Smith123");
        user.setPassword("iAmJohnSmith123");
        user.setAge(22);
        assertEquals(user, service.register(user));
    }
}
