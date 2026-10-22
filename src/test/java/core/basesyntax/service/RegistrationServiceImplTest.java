package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl service = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User("newuser", "password", 20);
    }

    @Test
    void registerUserNull_notOk() {
        assertThrows(IncorrectDataForRegistrationException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(IncorrectDataForRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(IncorrectDataForRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(IncorrectDataForRegistrationException.class, () -> service.register(user));
    }

    @Test
    void registerValidUser_ok() {
        User result = service.register(user);
        assertNotNull(result);
    }

    @Test
    void registerLoginTooShort_notOk() {
        user.setLogin("user");
        assertThrows(IncorrectDataForRegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginLengthFive_notOk() {
        user.setLogin("12345");
        assertThrows(IncorrectDataForRegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginLengthSix_ok() {
        user.setLogin("123456");
        User registeredUser = service.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void registerPasswordTooShort_notOk() {
        user.setPassword("word");
        assertThrows(IncorrectDataForRegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLengthFive_notOk() {
        user.setPassword("12345");
        assertThrows(IncorrectDataForRegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLengthSix_ok() {
        user.setPassword("123456");
        User registeredUser = service.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("");
        assertThrows(IncorrectDataForRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageTooYoung_notOk() {
        user.setAge(10);
        assertThrows(IncorrectDataForRegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_ageEighteen_ok() {
        user.setAge(18);
        User registeredUser = service.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(IncorrectDataForRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(user);
        User duplicateUser = new User();
        duplicateUser.setLogin("newuser");
        duplicateUser.setPassword("anotherPassword");
        duplicateUser.setAge(25);
        assertThrows(IncorrectDataForRegistrationException.class,
                () -> service.register(duplicateUser));
    }

    @Test
    void register_verifyAddedToStorage_ok() {
        service.register(user);
        assertEquals(1, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }
}
