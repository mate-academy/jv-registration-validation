package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.InputDataException;
import core.basesyntax.exeption.LoginAlreadyExistsException;
import core.basesyntax.exeption.UserIsNullException;
import core.basesyntax.exeption.UsersAgeException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        User bob = new User();
        bob.setAge(18);
        bob.setLogin("bob_2000");
        bob.setPassword("12345678");
        storageDao.add(bob);
        User alice = new User();
        alice.setAge(27);
        alice.setLogin("alice27");
        alice.setPassword("1111111");
        storageDao.add(alice);
        User mark = new User();
        mark.setAge(65);
        mark.setLogin("mark0908");
        mark.setPassword("apoeuhgb");
        storageDao.add(mark);
    }

    @Test
    public void register_newUser_ok() {
        User expected = new User();
        expected.setAge(22);
        expected.setLogin("new_User");
        expected.setPassword("19283746");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(storageDao.get("new_User"), actual);
    }

    @Test
    public void register_minAge_Ok() {
        User expected = new User();
        expected.setAge(18);
        expected.setLogin("new_User_18");
        expected.setPassword("66666666");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(storageDao.get("new_User_18"), actual);
    }

    @Test
    public void register_minLoginLength_Ok() {
        User expected = new User();
        expected.setAge(19);
        expected.setLogin("123456");
        expected.setPassword("7777777777");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(storageDao.get("123456"), actual);
    }

    @Test
    public void register_minPasswordLength_Ok() {
        User expected = new User();
        expected.setAge(90);
        expected.setLogin("new_User2026");
        expected.setPassword("123456");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(storageDao.get("new_User2026"), actual);
    }

    @Test
    public void register_nullAge_notOk() {
        User expected = new User();
        expected.setAge(null);
        expected.setLogin("new_User_nullAge");
        expected.setPassword("19283746");
        assertThrows(UsersAgeException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("new_User");
        expected.setPassword(null);
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_nullLogin_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin(null);
        expected.setPassword("16283940");
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_incorrectLogin_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("vvvvv");
        expected.setPassword("16283940");
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_incorrectPassword_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("newUser18");
        expected.setPassword("12345");
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_loginExists_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("bob_2000");
        expected.setPassword("999999999");
        assertThrows(LoginAlreadyExistsException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_smallAge_notOk() {
        User expected = new User();
        expected.setAge(10);
        expected.setLogin("newUser10");
        expected.setPassword("999999999");
        assertThrows(UsersAgeException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_negativeAge_notOk() {
        User expected = new User();
        expected.setAge(-12);
        expected.setLogin("newUser-12");
        expected.setPassword("999999999");
        assertThrows(UsersAgeException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_emptyUser_notOk() {
        User expected = new User();
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(UserIsNullException.class, () -> {
            registrationService.register(null);
        });
    }

}
