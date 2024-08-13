package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationServiceImpl;
    private User user;

    @BeforeEach
    void initializeObjects() {
        registrationServiceImpl = new RegistrationServiceImpl();
        user = new User();
        Storage.people.clear();
    }

    @Test
    void userIsValid_ok() {
        User existingUser = new User();
        existingUser.setLogin("differentLogin");
        Storage.people.add(existingUser);
        user.setAge(19);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(16);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(-5);
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_ageEdgeCase_notOk() {
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_ageEquals18_ok() {
        user.setAge(18);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_LoginExist_notOk() {
        User existingUser = new User();
        existingUser.setLogin("validLogin");
        Storage.people.add(existingUser);
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_LoginLength_notOk() {
        user.setAge(20);
        user.setLogin("log");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_LoginLengthEdgeCase_notOk() {
        user.setAge(20);
        user.setLogin("valid");
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_LoginLengthEquals6_Ok() {
        user.setAge(20);
        user.setLogin("validL");
        user.setPassword("validPass");
        User registeredUser = registrationServiceImpl.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_PasswordLength_notOk() {
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword("pass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_LoginNotNull_notOk() {
        user.setAge(20);
        user.setLogin(null);
        user.setPassword("validPass");
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_PasswordNotNull_notOk() {
        user.setAge(20);
        user.setLogin("validLogin");
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> registrationServiceImpl.register(user));
    }
}
