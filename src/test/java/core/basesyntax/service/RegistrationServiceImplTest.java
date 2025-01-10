package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidCredentialsException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int LOGIN_AND_PASSWORD_LEN = 6;
    private static final int AGE = 18;
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
        try {
            expected = registrationService.register(defaultUser);
        } catch (InvalidCredentialsException e) {
            throw new RuntimeException("User is registered");
        }
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
        StringBuilder login = new StringBuilder();
        for (int i = 1; i < LOGIN_AND_PASSWORD_LEN; i++) {
            login.append("a");
            defaultUser.setLogin(login.toString());
            assertThrows(InvalidCredentialsException.class,
                    () -> registrationService.register(defaultUser));
        }
    }

    @Test
    public void register_passwordLessThan6_notOk() {
        StringBuilder password = new StringBuilder();
        for (int i = 1; i < LOGIN_AND_PASSWORD_LEN; i++) {
            password.append("a");
            defaultUser.setPassword(password.toString());
            assertThrows(InvalidCredentialsException.class,
                    () -> registrationService.register(defaultUser));
        }
    }

    @Test
    public void register_ageLessThan18_notOk() {
        for (int age = 1; age < AGE; age++) {
            defaultUser.setAge(age);
            assertThrows(InvalidCredentialsException.class,
                    () -> registrationService.register(defaultUser));
        }
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
