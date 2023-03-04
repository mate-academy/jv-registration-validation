package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setLogin("potrap");
        user.setPassword("qwerty123");
        user.setAge(20);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(" ");
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for login [ ]");
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(" ");
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for password [ ]");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age null");
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        user.setAge(14);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age [14]");
    }

    @Test
    void register_tooBigAge_notOk() {
        user.setAge(150);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for age [150]");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for password null");
    }

    @Test
    void register_passwordShorterThanSixChars_notOk() {
        user.setPassword("bob1");
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for password [bob1]");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for login null");
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user null");
    }

    @Test
    void register_idIsNotNull_notOk() {
        user.setId(50L);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for id which is not null");
    }

    @Test
    void register_addTwoSameUsers_notOk() {
        registrationService.register(user);
        assertThrows(InvalidInputDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for two same users");
    }

    @Test
    void register_addTwoUsers_ok() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin("maksym");
        newUser.setPassword("ytrewq321");
        newUser.setAge(21);
        registrationService.register(newUser);
        assertEquals(Storage.people.get(0), user, "User on index 0 should be "
                + user.toString() + ",but was " + Storage.people.get(0));
        assertEquals(Storage.people.get(1), newUser, "user on index 1 should be "
                + newUser.toString() + ",but was " + Storage.people.get(1));
    }
}
