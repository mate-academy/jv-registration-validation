package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void initializeObject() {
        registrationService = new RegistrationServiceImpl();
        User correctUser1 = new User();
        correctUser1.setLogin("Kolia");
        correctUser1.setPassword("12345678");
        correctUser1.setAge(20);
        User correctUser2 = new User();
        correctUser2.setLogin("Vasyl");
        correctUser2.setPassword("13354899");
        correctUser2.setAge(18);
        Storage.people.add(correctUser1);
        Storage.people.add(correctUser2);
    }

    @Test
    void registerCorrectUser_Ok() {
        User user = new User();
        user.setAge(20);
        user.setLogin("Hello");
        user.setPassword("12345678");
        User actual = registrationService.register(user);
        boolean cont = Storage.people.contains(actual);
        assertTrue(cont);
    }

    @Test
    void register_NullUser_NotOk() {
        User user = null;
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWithIncorrectAge_NotOk() {
        User user = new User();
        user.setAge(17);
        user.setPassword("456543215");
        user.setLogin("Leyla2020");
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setPassword("7364234234");
        user.setLogin("euro2012");
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_Ok() {
        User user = new User();
        user.setPassword("6546435345");
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User();
        user.setLogin("Bob");
        user.setPassword("12345");
        user.setAge(20);
        try {
            registrationService.register(user);
        } catch (InvalidUserException e) {
            return;
        }
        fail("This password is null");
    }

    @Test
    void register_IncorrectPassword_NotOk() {
        User user = new User();
        user.setLogin("Bob");
        user.setAge(20);
        User lastUser = new User();
        lastUser.setLogin("Bob");
        lastUser.setPassword("12345");
        lastUser.setAge(20);
        try {
            registrationService.register(user);
        } catch (InvalidUserException e) {
            return;
        }
        fail("This password is short");
    }

    @Test
    void registrationCopyUser_notOk() {
        User copyUser = new User();
        copyUser.setLogin("Kolia");
        copyUser.setPassword("123545678");
        copyUser.setAge(25);
        assertThrows(InvalidUserException.class, () -> registrationService.register(copyUser));
    }

    @Test
    void registerWithExactlyAge() {
        User user = new User();
        user.setLogin("lego");
        user.setAge(18);
        user.setPassword("12345678");
        User register = registrationService.register(user);
        assertEquals(register, user);
    }
}
