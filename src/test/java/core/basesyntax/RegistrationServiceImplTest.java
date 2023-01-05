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
    void register_User_Ok() {
        assertEquals(user, regService.register(user),
                "User was created " + user);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Login can't be null");
    }

    @Test
    void register_EmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Login can't be empty");
    }

    @Test
    void register_PasswordEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Password cant't be empty");
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "Password cant be null");
    }

    @Test
    void register_ShortPasswordLength_NotOk() {
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "This password to short, must be 8 symbols");
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        regService.register(user);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                " This user can't created, it already exist");
    }

    @Test
    void register_YoungAge_NotOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "This user is to young, minimum age 18");
    }

    @Test
    void register_VeryBigAge_NotOk() {
        user.setAge(Integer.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "This age is incorrect, must be from 18 to 120");
    }

    @Test
    void register_IncorrectAge_NotOk() {
        user.setAge(Integer.MIN_VALUE);
        assertThrows(RuntimeException.class, () -> regService.register(user),
                "This age is incorrect, must be from 18 to 120");
    }
}
