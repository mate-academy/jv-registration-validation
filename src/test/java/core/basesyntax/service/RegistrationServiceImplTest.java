package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 5;
    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final String LOGIN_WITH_MIN_LENGTH = "io0p1";
    private static final String PASSWORD_WITH_MIN_LENGTH = "12345";

    @Test
    public void registerNullNotOk () {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void registerNullLoginNotOk () {
        User user = getValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerNullPasswordNotOk () {
        User user = getValidUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerNullAgeNotOk () {
        User user =  getValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void registerAddLoginNotOk () {
        User user =  getValidUser();
        StringBuilder login = new StringBuilder();
        user.setLogin(login.toString());

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        for (int i = 1; i < MIN_LOGIN_LENGTH; i++) {
            user.setLogin(login.append(i).toString());
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    public void registerAlredyRegisteredUserNotOk () {
        User user = getValidUser();
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    public void registerAddPasswordNotOk () {
        User user =  getValidUser();
        StringBuilder password = new StringBuilder();
        user.setPassword(password.toString());

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        for (int i = 1; i < MIN_PASSWORD_LENGTH; i++) {
            user.setPassword(password.append(i).toString());
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    public void registerAddAgeNotOk () {
        User user =  getValidUser();

        for (int i = 0; i < MIN_AGE; i++) {
            user.setAge(i);
            assertThrows(RegistrationException.class, () -> registrationService.register(user));
        }
    }

    @Test
    public void registerAddOk () {
        User user = getValidUser();
        assertEquals(registrationService.register(user), user);

        for (int i = 0; i <= 100; i++) {
            User newUser = new User();
            newUser.setLogin(user.getLogin() + i);
            newUser.setPassword(user.getPassword() + i);
            newUser.setAge(user.getAge() + i);
            assertEquals(registrationService.register(newUser), newUser);
        }
    }

    @Test
    public void registerAddMinimumValuesOk () {
        User user = getValidUser();
        user.setLogin(LOGIN_WITH_MIN_LENGTH);
        user.setPassword(PASSWORD_WITH_MIN_LENGTH);
        user.setAge(MIN_AGE);
        assertEquals(registrationService.register(user), user);
    }

    private User getValidUser() {
        User validUser = new User();
        validUser.setLogin("lqwerty123");
        validUser.setPassword("qwe123qwe");
        validUser.setAge(23);
        return validUser;
    }
}