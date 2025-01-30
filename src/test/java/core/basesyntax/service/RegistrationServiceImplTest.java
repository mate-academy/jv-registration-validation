package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(CustomException.class, () -> service.register(null));
    }

    @Test
    void register_StorageHasUserWithSuchLogin_NotOk() {
        User storedUser = new User();
        storedUser.setLogin("existingUser");
        Storage.people.add(storedUser);
        user.setLogin("existingUser");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_StorageDoNotHaveUser_Ok() {
        user.setLogin("notExistingUser");
        user.setPassword("validPassword");
        user.setAge(25);
        User expected = user;
        User actual = service.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_LoginIsLessThan6Characters_NotOK() {
        StringBuilder login = new StringBuilder();
        for (char letter = 'a'; letter < 'f'; letter++) {
            user.setLogin(login.toString());
            login.append(letter);
            assertThrows(CustomException.class, () -> service.register(user));
        }
    }

    @Test
    void register_LoginIsAtLeast6Characters_OK() {
        StringBuilder login = new StringBuilder("6chars");
        for (char ch = 'a'; ch < 'f'; ch++) {
            user.setPassword("validPassword");
            user.setAge(25);
            user.setLogin(login.toString());
            User expected = user;
            User actual = service.register(user);
            assertNotNull(actual);
            assertEquals(expected, actual);
            login.append(ch);
            user = new User();
        }
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setLogin("validLogin");
        user.setPassword(null);
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_PasswordIsLessThan6Characters_NotOK() {
        user.setLogin("validLogin");
        StringBuilder password = new StringBuilder();
        for (char letter = 'a'; letter < 'f'; letter++) {
            user.setPassword(password.toString());
            password.append(letter);
            assertNotNull(user);
            assertThrows(CustomException.class, () -> service.register(user));
        }
    }

    @Test
    void register_PasswordIsAtLeast6Characters_OK() {
        StringBuilder login = new StringBuilder("anotherValidLogin");
        StringBuilder password = new StringBuilder("sixLet");
        for (char letter = 'a'; letter < 'f'; letter++) {
            user.setLogin(login.toString());
            user.setPassword(password.toString());
            user.setAge(25);
            login.append(letter);
            password.append(letter);
            assertNotNull(user);
            assertEquals(service.register(user), user);
            user = new User();
        }
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        for (int age = -4; age < 18; age += 4) {
            user.setAge(age);
            assertNotNull(user);
            assertThrows(CustomException.class, () -> service.register(user));
        }
    }

    @Test
    void register_AgeIsGreaterThan18_Ok() {
        int testAge = 50;
        for (int age = 18; age < testAge; age += 10) {
            user.setLogin("validLogin" + age);
            user.setPassword("validPassword");
            user.setAge(age);
            assertNotNull(user);
            assertEquals(service.register(user), user);
            user = new User();
        }
    }
}
