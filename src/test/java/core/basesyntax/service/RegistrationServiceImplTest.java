package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();
    private StorageDao storageDao;

    @BeforeEach
    public void set_Up() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(null),
                "Invalid user date. User fields cannot be null");
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin("ValidUser");
        validUser.setPassword("validPassword");
        validUser.setAge(20);

        registrationServiceImpl.register(validUser);
        assertNotNull(storageDao.get("ValidUser"));

        User firstUser = new User();
        firstUser.setLogin("FirstUser");
        firstUser.setPassword("firstPassword");
        firstUser.setAge(20);

        User secondUser = new User();
        secondUser.setLogin("SecondUser");
        secondUser.setPassword("secondPassword");
        secondUser.setAge(25);

        registrationServiceImpl.register(firstUser);
        registrationServiceImpl.register(secondUser);

        assertNotNull(storageDao.get("FirstUser"));
        assertNotNull(storageDao.get("SecondUser"));

        validUser.setLogin("UserLogin6");
        assertTrue(Storage.people.add(validUser));

        validUser.setLogin("UserLogin7");
        assertTrue(Storage.people.add(validUser));

        validUser.setLogin("UserLogin10");
        assertTrue(Storage.people.add(validUser));

        validUser.setPassword("abcdef");
        assertTrue(Storage.people.add(validUser));

        validUser.setPassword("abcdefg");
        assertTrue(Storage.people.add(validUser));

        validUser.setPassword("abcdefghij");
        assertTrue(Storage.people.add(validUser));

        validUser.setAge(18);
        assertTrue(Storage.people.add(validUser));

        validUser.setAge(19);
        assertTrue(Storage.people.add(validUser));

        validUser.setAge(25);
        assertTrue(Storage.people.add(validUser));

        validUser.setAge(60);
        assertTrue(Storage.people.add(validUser));

        validUser.setAge(Integer.MAX_VALUE);
        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("NewUser");
        firstUser.setPassword("secondPassword");
        firstUser.setAge(25);

        registrationServiceImpl.register(firstUser);

        User secondUser = new User();
        secondUser.setLogin("NewUser");
        secondUser.setPassword("secondPassword");
        secondUser.setAge(30);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(secondUser),
                "User with this login already exists");

        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Username length null. Login must be at least 6 characters long");

        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Username length 0. Login must be at least 6 characters long");

        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Username length 3. Login must be at least 6 characters long");

        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Username length 5. Login must be at least 6 characters long");
    }

    @Test
    public void register_invalidPassword_notOk() {
        User user = new User();
        user.setLogin("UserInvalidPassword");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Keywords length null. Password must be at least 6 characters long");

        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Keywords length 0. Password must be at least 6 characters long");

        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Keywords length 3. Password must be at least 6 characters long");

        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Keywords length 5. Password must be at least 6 characters long");
    }

    @Test
    public void register_invalidAge_notOk() {
        User user = new User();
        user.setLogin("UserAgeNull");
        user.setPassword("password");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: null. User must be at least 18 years old");

        user.setAge(-6);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: -6. User must be at least 18 years old");

        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: -18. User must be at least 18 years old");

        user.setAge(-60);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: -60. User must be at least 18 years old");

        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: 0. User must be at least 18 years old");

        user.setAge(6);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: 6. User must be at least 18 years old");

        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "Age: 17. User must be at least 18 years old");

        user.setAge(Integer.MIN_VALUE);
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user),
                "User must be at least 18 years old");
    }
}
