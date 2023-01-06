package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl regService = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Ivan_Ivanov");
        user.setPassword("qwerty123456");
        user.setAge(23);
        Storage.people.clear();
    }

    @Test
    void register_addAdultUser_ok() {
        assertEquals(user, regService.register(user),
                "User: " + user + " should be created");
    }

    @Test
    void register_addNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Login can't be null");
    }

    @Test
    void register_addEmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Login can't be empty");
    }

    @Test
    void register_addPasswordEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Password can't be empty");
    }

    @Test
    void register_addNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Password cant be null");
    }

    @Test
    void register_addShortPasswordLength_NotOk() {
        user.setPassword("abcde");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "This password to short, must be 6 symbols");
    }

    @Test
    void register_addUserAlreadyExist_NotOk() {
        User user = new User();
        user.setLogin("Ivan_Ivanov");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                " This user can't created, it already exist");
    }

    @Test
    void register_addYoungAge_NotOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "User age should be greater than 18");
    }

    @Test
    void register_addIncorrectAge_NotOk() {
        user.setAge(Integer.MIN_VALUE);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "User age should be greater than 18");
    }

    @Test
    void register_addYoungAge_ShortPassword_NotOk() {
        user.setPassword("12345");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "This password: " + user.getPassword() + " to short, must be 6 symbols."
                + " User age should be greater than 18");
    }
}

