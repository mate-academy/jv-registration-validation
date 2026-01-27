package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InputDataException;
import core.basesyntax.exeption.LoginAlreadyExistsException;
import core.basesyntax.exeption.UserIsNullException;
import core.basesyntax.exeption.UsersAgeException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
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
    public void add_newUser_ok() {
        User expected = new User();
        expected.setAge(22);
        expected.setLogin("new_User");
        expected.setPassword("19283746");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(storageDao.get("new_User"), actual);
    }

    @Test
    public void add_newUsers_ok() {
        User expected = new User();
        expected.setAge(22);
        expected.setLogin("new_User_Ok");
        expected.setPassword("19283746");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(storageDao.get("new_User_Ok"), actual);
        User expected2 = new User();
        expected2.setAge(30);
        expected2.setLogin("new_User2_Ok");
        expected2.setPassword("123456789");
        User actual2 = registrationService.register(expected2);
        assertEquals(expected2, actual2);
        assertEquals(storageDao.get("new_User2_Ok"), actual2);
    }

    @Test
    public void addUser_nullAge_notOk() {
        User expected = new User();
        expected.setAge(null);
        expected.setLogin("new_User");
        expected.setPassword("19283746");
        assertThrows(UsersAgeException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_nullPassword_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("new_User");
        expected.setPassword(null);
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_nullLogin_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin(null);
        expected.setPassword("16283940");
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_incorrectLogin_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("vvvv");
        expected.setPassword("16283940");
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_incorrectPassword_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("newUser18");
        expected.setPassword("64");
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_loginExists_notOk() {
        User expected = new User();
        expected.setAge(33);
        expected.setLogin("bob_2000");
        expected.setPassword("999999999");
        assertThrows(LoginAlreadyExistsException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_smallAge_notOk() {
        User expected = new User();
        expected.setAge(10);
        expected.setLogin("newUser10");
        expected.setPassword("999999999");
        assertThrows(UsersAgeException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void addUser_negativeAge_notOk() {
        User expected = new User();
        expected.setAge(-12);
        expected.setLogin("newUser-12");
        expected.setPassword("999999999");
        assertThrows(UsersAgeException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void add_emptyUser_notOk() {
        User expected = new User();
        assertThrows(InputDataException.class, () -> {
            registrationService.register(expected);
        });
    }

    @Test
    public void add_nullUser_notOk() {
        assertThrows(UserIsNullException.class, () -> {
            registrationService.register(null);
        });
    }

}
