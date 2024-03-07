package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registration;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        registration = new RegistrationServiceImpl();
    }

    @Test
    void register_userNotNull_ok() {
        user1 = new User("userLogin", "password", 18);
        user2 = new User("user22", "password", 19);
        assertEquals(user1, registration.register(user1));
        assertEquals(user2, registration.register(user2));
    }

    @Test
    void register_userIsNull_notOk() {
        user1 = null;
        assertThrows(RegistrationException.class, () -> registration.register(user1));
    }

    @Test
    void register_notFindSuchLogin_ok() {
        user1 = new User("differentLogin1", "password", 18);
        user2 = new User("differentLogin2", "password", 18);
        assertEquals(user1, registration.register(user1));
        assertEquals(user2, registration.register(user2));
    }

    @Test
    void register_findSuchLogin_notOK() {
        user1 = new User("login", "password", 18);
        user2 = new User("login", "password", 18);
        Storage.people.add(user1);
        assertThrows(RegistrationException.class, () -> registration.register(user2));
    }

    @Test
    void register_loginNotNull_ok() {
        user1 = new User("notNull", "password", 18);
        assertEquals(user1, registration.register(user1));
    }

    @Test
    void register_loginIsNull_notOk() {
        user1 = new User(null, "password", 20);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
    }

    @Test
    void register_loginIsValid_ok() {
        user1 = new User("validLogin", "password", 18);
        user2 = new User("alsoValidLogin", "password", 20);
        user3 = new User("edgeLo", "password", 19);
        assertEquals(user1, registration.register(user1));
        assertEquals(user2, registration.register(user2));
        assertEquals(user3, registration.register(user3));
    }

    @Test
    void register_loginNotValid_notOk() {
        user1 = new User("edge", "password", 18);
        user2 = new User("login", "password", 20);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
        assertThrows(RegistrationException.class, () -> registration.register(user2));
    }

    @Test
    void register_passwordNotNull_ok() {
        user1 = new User("uusseerr", "password", 18);
        assertEquals(user1, registration.register(user1));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user1 = new User("userLogin",null, 20);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
    }

    @Test
    void register_passwordValid_ok() {
        user1 = new User("userTest", "edgeca", 20);
        user2 = new User("userLogin22", "ppaasswwoorrdd", 21);
        assertEquals(user1, registration.register(user1));
        assertEquals(user2, registration.register(user2));
    }

    @Test
    void register_passwordNotValid_notOk() {
        user1 = new User("userLogin", "cedge", 20);
        user2 = new User("userLogin2", "dge", 20);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
        assertThrows(RegistrationException.class, () -> registration.register(user2));
    }

    @Test
    void register_ageNotNull_ok() {
        user1 = new User("userLoginAge", "password", 45);
        assertEquals(user1, registration.register(user1));
    }

    @Test
    void register_ageIsNull_notOk() {
        user1 = new User("userLogin", "password", null);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
    }

    @Test
    void register_ageIsValid_ok() {
        user1 = new User("userLogin1", "password", 18);
        user2 = new User("userLogin2", "password", 78);
        user3 = new User("userLogin3", "password", 100);
        assertEquals(user1, registration.register(user1));
        assertEquals(user2, registration.register(user2));
        assertEquals(user3, registration.register(user3));
    }

    @Test
    void register_ageNotValid_notOK() {
        user1 = new User("userLogin1", "password", 17);
        user2 = new User("userLogin2", "password", 10);
        user3 = new User("userLogin3", "password", -1);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
        assertThrows(RegistrationException.class, () -> registration.register(user2));
        assertThrows(RegistrationException.class, () -> registration.register(user3));
    }
}
