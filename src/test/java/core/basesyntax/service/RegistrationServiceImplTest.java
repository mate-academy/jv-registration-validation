package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl regService = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Ivan_Litvynets");
        user.setPassword("qwerty123456");
        user.setAge(23);
        Storage.people.clear();
    }

    @Test
    void register_User_Ok() {
        assertEquals(user, regService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_LoginWithSpecialSymbols_NotOk() {
        user.setLogin("&Ivan_Litvynets");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_PasswordEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_ShortPasswordLength_NotOk() {
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        regService.register(user);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_YoungAge_NotOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_VeryBigAge_NotOk() {
        user.setAge(Integer.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_IncorrectAge_NotOk() {
        user.setAge(Integer.MIN_VALUE);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }
}
