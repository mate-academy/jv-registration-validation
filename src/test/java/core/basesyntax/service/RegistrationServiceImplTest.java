package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;
    private User user;

    @BeforeEach
    public void setUp() {
        service = new RegistrationServiceImpl();
        user = new User();
        people.clear();
    }

    @Test
    public void register_userIsNull_notOk() {
        assertThrows(CustomException.class, () -> service.register(null));
    }

    @Test
    public void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_ageIsLessThenExpected_NotOk() {
        user.setAge(16);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_passwordLengthIsLessThenExpected_NotOk() {
        user.setPassword("34gd");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_loginLengthIsLessThenExpected_NotOk() {
        user.setLogin("Bob");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    public void register_userAlreadyExistInDb_NotOk() {
        user.setAge(18);
        user.setLogin("Bobby123");
        user.setPassword("123456");

        service.register(user);

        User user2 = new User();
        user2.setAge(18);
        user2.setLogin("Bobby123");
        user2.setPassword("123456");

        assertEquals(user, user2);
        assertThrows(CustomException.class, () -> service.register(user2));
    }

    @Test
    public void register_addUserInStorage_Ok() {
        user.setAge(18);
        user.setLogin("Bobby123");
        user.setPassword("123456");

        service.register(user);

        User user2 = new User();
        user2.setAge(19);
        user2.setLogin("Scot123");
        user2.setPassword("123456");

        assertNotEquals(user, user2);

        User expected = user2;
        User actual = service.register(user2);
        assertEquals(expected, actual);

        int storageLength = people.size();
        assertEquals(2, storageLength);
    }
}
