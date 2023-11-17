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
    private RegistrationServiceImpl registrationServiceImpl;
    private StorageDao storageDao;

    @BeforeEach
    public void set_Up() {
        storageDao = new StorageDaoImpl();
        registrationServiceImpl = new RegistrationServiceImpl();
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

        User validFirstUser = new User();
        validFirstUser.setLogin("FirstUser");
        validFirstUser.setPassword("firstPassword");
        validFirstUser.setAge(20);

        User validSecondUser = new User();
        validSecondUser.setLogin("SecondUser");
        validSecondUser.setPassword("secondPassword");
        validSecondUser.setAge(25);

        registrationServiceImpl.register(validFirstUser);
        registrationServiceImpl.register(validSecondUser);
        assertNotNull(storageDao.get("FirstUser"));
        assertNotNull(storageDao.get("SecondUser"));

        User userLoginLength6 = new User();
        userLoginLength6.setLogin("abcdef");
        userLoginLength6.setPassword("validPassword");
        userLoginLength6.setAge(20);
        assertTrue(Storage.people.add(userLoginLength6));

        User userLoginLength7 = new User();
        userLoginLength7.setLogin("abcdefg");
        userLoginLength7.setPassword("validPassword");
        userLoginLength7.setAge(20);
        assertTrue(Storage.people.add(userLoginLength7));

        User userLoginLength10 = new User();
        userLoginLength10.setLogin("abcdefghij");
        userLoginLength10.setPassword("validPassword");
        userLoginLength10.setAge(20);
        assertTrue(Storage.people.add(userLoginLength10));

        User userPasswordLength6 = new User();
        userPasswordLength6.setLogin("ValidUser");
        userPasswordLength6.setPassword("abcdef");
        userPasswordLength6.setAge(20);
        assertTrue(Storage.people.add(userPasswordLength6));

        User userPasswordLength7 = new User();
        userPasswordLength7.setLogin("ValidUser");
        userPasswordLength7.setPassword("abcdefg");
        userPasswordLength7.setAge(20);
        assertTrue(Storage.people.add(userPasswordLength7));

        User userPasswordLength10 = new User();
        userPasswordLength10.setLogin("ValidUser");
        userPasswordLength10.setPassword("abcdefghij");
        userPasswordLength10.setAge(20);
        assertTrue(Storage.people.add(userPasswordLength10));

        User userAge18 = new User();
        userAge18.setLogin("ValidUser");
        userAge18.setPassword("validPassword");
        userAge18.setAge(18);
        assertTrue(Storage.people.add(userAge18));

        User userAge19 = new User();
        userAge19.setLogin("ValidUser");
        userAge19.setPassword("validPassword");
        userAge19.setAge(19);
        assertTrue(Storage.people.add(userAge19));

        User userAge25 = new User();
        userAge25.setLogin("ValidUser");
        userAge25.setPassword("validPassword");
        userAge25.setAge(25);
        assertTrue(Storage.people.add(userAge25));

        User userAge60 = new User();
        userAge60.setLogin("ValidUser");
        userAge60.setPassword("validPassword");
        validUser.setAge(60);
        assertTrue(Storage.people.add(userAge60));

        User userAgeMaxInt = new User();
        userAgeMaxInt.setLogin("ValidUser");
        userAgeMaxInt.setPassword("validPassword");
        userAgeMaxInt.setAge(Integer.MAX_VALUE);
        assertTrue(Storage.people.add(userAgeMaxInt));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User validUserLogin = new User();
        validUserLogin.setLogin("NewUser");
        validUserLogin.setPassword("Password");
        validUserLogin.setAge(25);

        registrationServiceImpl.register(validUserLogin);

        User repeatedUserLogin = new User();
        repeatedUserLogin.setLogin("NewUser");
        repeatedUserLogin.setPassword("Password");
        repeatedUserLogin.setAge(30);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(repeatedUserLogin),
                "User with this login already exists");

        User userLoginNull = new User();
        userLoginNull.setLogin(null);
        userLoginNull.setPassword("password");
        userLoginNull.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userLoginNull),
                "Username length null. Login must be at least 6 characters long");

        User userLoginLength0 = new User();
        userLoginLength0.setLogin("");
        userLoginLength0.setPassword("validPassword");
        userLoginLength0.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userLoginLength0),
                "Username length 0. Login must be at least 6 characters long");

        User userLoginLength3 = new User();
        userLoginLength3.setLogin("abc");
        userLoginLength3.setPassword("validPassword");
        userLoginLength3.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userLoginLength3),
                "Username length 3. Login must be at least 6 characters long");

        User userLoginLength5 = new User();
        userLoginLength5.setLogin("abcde");
        userLoginLength5.setPassword("validPassword");
        userLoginLength5.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userLoginLength5),
                "Username length 5. Login must be at least 6 characters long");
    }

    @Test
    public void register_invalidPassword_notOk() {
        User userPasswordNull = new User();
        userPasswordNull.setLogin("UserInvalidPassword");
        userPasswordNull.setPassword(null);
        userPasswordNull.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userPasswordNull),
                "Keywords length null. Password must be at least 6 characters long");

        User userPasswordLength0 = new User();
        userPasswordLength0.setLogin("UserInvalidPassword");
        userPasswordLength0.setPassword("");
        userPasswordLength0.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userPasswordLength0),
                "Keywords length 0. Password must be at least 6 characters long");

        User userPasswordLength3 = new User();
        userPasswordLength3.setLogin("UserInvalidPassword");
        userPasswordLength3.setPassword("abc");
        userPasswordLength3.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userPasswordLength3),
                "Keywords length 3. Password must be at least 6 characters long");

        User userPasswordLength5 = new User();
        userPasswordLength5.setLogin("UserInvalidPassword");
        userPasswordLength5.setPassword("abcde");
        userPasswordLength5.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userPasswordLength5),
                "Keywords length 5. Password must be at least 6 characters long");
    }

    @Test
    public void register_invalidAge_notOk() {
        User userAgeNull = new User();
        userAgeNull.setLogin("UserAgeNull");
        userAgeNull.setPassword("password");
        userAgeNull.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAgeNull),
                "Age: null. User must be at least 18 years old");

        User userAgeNegative6 = new User();
        userAgeNegative6.setLogin("UserAge0");
        userAgeNegative6.setPassword("password");
        userAgeNegative6.setAge(-6);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAgeNegative6),
                "Age: -6. User must be at least 18 years old");

        User userAgeNegative18 = new User();
        userAgeNegative18.setLogin("UserAgeNegative18");
        userAgeNegative18.setPassword("password");
        userAgeNegative18.setAge(-18);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAgeNegative18),
                "Age: -18. User must be at least 18 years old");

        User userAgeNegative60 = new User();
        userAgeNegative60.setLogin("UserAgeNegative60");
        userAgeNegative60.setPassword("password");
        userAgeNegative60.setAge(-60);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAgeNegative60),
                "Age: -60. User must be at least 18 years old");

        User userAge0 = new User();
        userAge0.setLogin("UserAge0");
        userAge0.setPassword("password");
        userAge0.setAge(0);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAge0),
                "Age: 0. User must be at least 18 years old");

        User userAge6 = new User();
        userAge6.setLogin("UserAge6");
        userAge6.setPassword("password");
        userAge6.setAge(6);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAge6),
                "Age: 6. User must be at least 18 years old");

        User userAge17 = new User();
        userAge17.setLogin("UserAge17");
        userAge17.setPassword("password");
        userAge17.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAge17),
                "Age: 17. User must be at least 18 years old");

        User userAgeMinInt = new User();
        userAgeMinInt.setLogin("UserAgeMinInt");
        userAgeMinInt.setPassword("password");
        userAgeMinInt.setAge(Integer.MIN_VALUE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(userAgeMinInt),
                "Wrong age. User must be at least 18 years old");
    }

}
