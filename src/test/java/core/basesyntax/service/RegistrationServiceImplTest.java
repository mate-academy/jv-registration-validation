package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
    }

    @Test
    public void user_register_with_short_login_notOK() {
        User userWithShortLogin = new User();
        userWithShortLogin.setPassword("123456");
        userWithShortLogin.setId(1234321L);
        userWithShortLogin.setLogin("231");
        userWithShortLogin.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithShortLogin);
        });

        assertEquals("This login is to short,  min 6 characters", exception.getMessage());

    }

    @Test
    public void user_register_with_short_passwors_notOK() {
        User userWithShortPassword = new User();
        userWithShortPassword.setPassword("12345");
        userWithShortPassword.setId(1234321L);
        userWithShortPassword.setLogin("tapichok");
        userWithShortPassword.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithShortPassword);
        });

        assertEquals("This pass is to short,  min 6 characters", exception.getMessage());

    }

    @Test
    public void user_register_with_null_password_notOK() {
        User userWithNullPassword = new User();
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setId(1234321L);
        userWithNullPassword.setLogin("tapichok");
        userWithNullPassword.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullPassword);
        });

        assertEquals("Password can't be null", exception.getMessage());

    }

    @Test
    public void user_register_with_null_login_notOK() {
        User userWithNullLogin = new User();
        userWithNullLogin.setPassword("123456");
        userWithNullLogin.setId(1234321L);
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullLogin);
        });

        assertEquals("Login can't be null", exception.getMessage());

    }

    @Test
    public void user_register_with_a_lack_of_years_notOK() {
        User userWithALackOfYears = new User();
        userWithALackOfYears.setPassword("1234567");
        userWithALackOfYears.setId(1234321L);
        userWithALackOfYears.setLogin("chinazes");
        userWithALackOfYears.setAge(15);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithALackOfYears);
        });

        assertEquals("Not valid age: " + userWithALackOfYears.getAge()
                + ". Min allowed age is " + MIN_AGE, exception.getMessage());
    }

    @Test
    public void user_register_with_identical_login() {
        User testUser = new User();
        testUser.setPassword("1234567");
        testUser.setId(1234321L);
        testUser.setLogin("AbraCadabra");
        testUser.setAge(20);
        registrationService.register(testUser);

        User userWithIdenticalLogin = new User();
        userWithIdenticalLogin.setPassword("1234567");
        userWithIdenticalLogin.setId(1232321L);
        userWithIdenticalLogin.setLogin("AbraCadabra");
        userWithIdenticalLogin.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithIdenticalLogin);
        });

        assertEquals("This login is already in use", exception.getMessage());
    }

    @Test
    public void user_register_with_ok_data_OK() {
        User userWithOkData = new User();
        userWithOkData.setPassword("1234567");
        userWithOkData.setId(1234321L);
        userWithOkData.setLogin("AbraCadabra");
        userWithOkData.setAge(18);
        User returnedUser = storageDao.add(userWithOkData);

        assertEquals(userWithOkData, returnedUser);
    }
}
