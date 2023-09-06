package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl regService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_allParamsValid_ok() {
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword("123456790");
        User actual = regService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setAge(18);
        user.setPassword("123456790");
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("validLogin");
        user.setAge(null);
        user.setPassword("123456790");
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> regService.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin("a");
        user.setAge(18);
        user.setPassword("123456790");
        assertThrows(RegistrationServiceException.class, () -> regService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        user.setAge(18);
        user.setPassword("123456790");
        assertThrows(RegistrationServiceException.class, () -> regService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setLogin("validLogin");
        user.setAge(12);
        user.setPassword("123456790");
        assertThrows(RegistrationServiceException.class, () -> regService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword("1234");
        assertThrows(RegistrationServiceException.class, () -> regService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword("12345678");
        regService.register(user);
        assertThrows(RegistrationServiceException.class, () -> regService.register(user));
    }
}
