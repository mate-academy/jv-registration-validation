package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void register_validUser_Ok() {
        User expected = new User();
        expected.setLogin("Oleksandr1");
        expected.setPassword("123456789");
        expected.setAge(20);
        registrationService.register(expected);
        User actual = storageDao.get("Oleksandr1");
        assertEquals(expected, actual);
    }

    @Test
    public void register_loginUnderSixChars_NotOk() {
        User expected = new User();
        expected.setLogin("Mark");
        expected.setPassword("123456789");
        expected.setAge(20);
        assertThrows(RegistrationServiceImpl.ShortLoginException.class,
                () -> registrationService.register(expected)); // change to custom exception
    }

    @Test
    public void register_passwordUnderSixChars_NotOk() {
        User expected = new User();
        expected.setLogin("Oleksandr2");
        expected.setPassword("1234");
        expected.setAge(20);
        assertThrows(RegistrationServiceImpl.ShortPasswordException.class,
                () -> registrationService.register(expected)); // change to custom exception
    }

    @Test
    public void register_underEighteenAge_NotOk() {
        User expected = new User();
        expected.setLogin("Oleksandr3");
        expected.setPassword("123456789");
        expected.setAge(16);
        assertThrows(RegistrationServiceImpl.UnderAgeUserException.class,
                () -> registrationService.register(expected)); // change to custom exception
    }

    @Test
    public void register_nullLogin_NotOk() {
        User expected = new User();
        expected.setPassword("123456789");
        expected.setAge(20);
        assertThrows(RegistrationServiceImpl.NullLoginException.class,
                () -> registrationService.register(expected));
    }

    @Test
    public void register_nullPassword_NotOk() {
        User expected = new User();
        expected.setLogin("Oleksandr4");
        expected.setAge(20);
        assertThrows(RegistrationServiceImpl.NullPasswordException.class,
                () -> registrationService.register(expected));
    }

    @Test
    public void register_nullAge_NotOk() {
        User expected = new User();
        expected.setLogin("Oleksandr5");
        expected.setPassword("123456789");
        assertThrows(RegistrationServiceImpl.NullAgeException.class,
                () -> registrationService.register(expected));
    }

    @Test
    public void register_identicalUserInStorage_NotOk() {
        User expected = new User();
        expected.setLogin("Oleksandr6");
        expected.setPassword("123456789");
        expected.setAge(20);
        storageDao.add(expected);
        assertThrows(RegistrationServiceImpl.UserAlreadyInSystemException.class,
                () -> registrationService.register(expected)); // change to custom exception
    }
}
