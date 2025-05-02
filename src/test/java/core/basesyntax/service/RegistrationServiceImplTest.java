package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidCredentialsException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int REQUIRED_AGE = 18;
    private static final String INCORRECT_LOGIN = "userT";
    private static final String INCORRECT_PASSWORD = "passT";
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private RegistrationServiceImpl registrationService;
    private User defaultUser;

    @BeforeEach
    public void createDefaultUser() {
        registrationService = new RegistrationServiceImpl();
        defaultUser = new User(1L, "default", "password", 18);
    }

    @Test
    public void register_userWithCorrectData_Ok() {
        User expected;
        expected = registrationService.register(defaultUser);
        assertEquals(defaultUser, expected);
    }

    @Test
    public void register_sameUsers_notOk() {
        storageDao.add(defaultUser);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_loginLessThan6_notOk() {
        defaultUser.setLogin(INCORRECT_LOGIN);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_passwordLessThan6_notOk() {
        defaultUser.setPassword(INCORRECT_PASSWORD);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_ageLessThan18_notOk() {
        defaultUser.setAge(REQUIRED_AGE - 1);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(defaultUser));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidCredentialsException.class,
                () -> registrationService.register(null));
    }
}
