package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.AlreadyExistException;
import core.basesyntax.exception.InvalidValueException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service = new RegistrationServiceImpl();
    private User userOk = new User("vasyl.ivaniv", "1955135", 33);
    private User user1 = new User("kkooll99", "19941993", 29);
    private User user1Same = new User("kkooll99", "19941993", 29);
    private User userWithSmallerLogin = new User("kkool", "19941993", 29);
    private User userWithSmallerPassword = new User("kkooll98", "1994", 29);
    private User userIsTooYoung = new User("kkooll20", "19941993", 17);

    @Test
    void addUserOk() {
        User actual = service.register(userOk);
        User expected = userOk;
        assertEquals(expected, actual);
    }

    @Test
    void addSameUserOk() {
        service.register(user1);
        assertThrows(AlreadyExistException.class, () -> {
            service.register(user1Same);
        });
    }

    @Test
    void addUserWithInvalidLoginOk() {
        assertThrows(InvalidValueException.class, () -> {
            service.register(userWithSmallerLogin);
        });
    }

    @Test
    void addUserWithInvalidPassword() {
        assertThrows(InvalidValueException.class, () -> {
            service.register(userWithSmallerPassword);
        });
    }

    @Test
    void addUserWithInvalidAge() {
        assertThrows(InvalidValueException.class, () -> {
            service.register(userIsTooYoung);
        });
    }

    @Test
    void addNullUser() {
        User actual = service.register(null);
        assertNull(actual);
    }
}
