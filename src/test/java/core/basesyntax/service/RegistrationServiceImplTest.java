package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    public static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        User user1 = new User();
        user1.setLogin("username");
        user1.setPassword("password");
        user1.setAge(22);
        User user2 = new User();
        user2.setLogin("username");
        user2.setPassword("password");
        user1.setAge(22);
        service.register(user1);
        Executable executable = () -> service.register(user2);
        assertThrows(RegistrationException.class, executable);
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User();
        user.setPassword("password");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLengthGreaterThanSix_ok() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(22);
        User registeredUser = service.register(user);
        assertEquals(registeredUser.getLogin(), user.getLogin());
    }

    @Test
    void register_loginLengthLessThanSix_notOk() {
        User user = new User();
        user.setLogin("name");
        user.setPassword("password");
        user.setAge(22);
        Executable executable = () -> service.register(user);
        assertThrows(RegistrationException.class, executable);
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setLogin("username");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordLengthGreaterThanSix_ok() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(22);
        User registeredUser = service.register(user);
        assertEquals(registeredUser.getPassword(), user.getPassword());
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("pass");
        user.setAge(22);
        Executable executable = () -> service.register(user);
        assertThrows(RegistrationException.class, executable);
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }
}