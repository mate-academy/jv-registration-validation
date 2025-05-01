package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationServiceImpl;

    @BeforeEach
    void setUp() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @Test
    void registration_addUser_ok() {
        User user1 = new User();
        user1.setLogin("Vortex");
        user1.setPassword("123456");
        user1.setAge(18);
        registrationServiceImpl.register(user1);
        assertEquals(1, Storage.people.size());
        assertEquals(user1, Storage.people.get(0));
    }

    @Test
    void registration_addUserWithSameLogin_notOk() {
        User user1 = new User();
        user1.setLogin("Terminator");
        user1.setPassword("123456");
        user1.setAge(18);
        User user2 = new User();
        user2.setLogin("Vortex");
        user2.setPassword("123456");
        user2.setAge(18);
        registrationServiceImpl.register(user1);
        registrationServiceImpl.register(user2);
        assertEquals(2, Storage.people.size());
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user1));
    }

    @Test
    void registration_addUserNull_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userLoginLengthLessValid_notOk() {
        User user1 = new User();
        user1.setLogin("Robot");
        User user2 = new User();
        user2.setLogin("R");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user1));
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user2));
    }

    @Test
    void registration_userLoginEmptyLine_notOk() {
        User user = new User();
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userLoginNull_notOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userPasswordLessValid_notOk() {
        User user = new User();
        user.setLogin("Vertex");
        user.setPassword("12345");
        User user2 = new User();
        user2.setLogin("Terminator");
        user2.setPassword("1");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user2));
    }

    @Test
    void registration_userPasswordEmptyLine_notOk() {
        User user = new User();
        user.setLogin("Vertex");
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userPasswordNull_notOk() {
        User user = new User();
        user.setLogin("Vertex");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userAgeLessValid_notOk() {
        User user = new User();
        user.setLogin("Vertex");
        user.setPassword("123456");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userAgeZero_notOk() {
        User user = new User();
        user.setLogin("Vertex");
        user.setPassword("123456");
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void registration_userAgeLessZero_notOk() {
        User user = new User();
        user.setLogin("Vertex");
        user.setPassword("123456");
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
