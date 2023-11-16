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
        registrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_invalidUserData_notOk() {
        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(null));
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin("ValidUser");
        validUser.setPassword("validPassword");
        validUser.setAge(20);

        registrationServiceImpl.register(validUser);

        assertNotNull(storageDao.get("ValidUser"));
    }

    @Test
    public void register_differentLogin_ok() {
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
    }

    @Test
    public void register_duplicateLogin_notOk() {
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
                () -> registrationServiceImpl.register(secondUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthLoginZero_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthLoginThree_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthLoginFive_notOk() {
        User user = new User();
        user.setLogin("abcde");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthLoginSix_ok() {
        User validUser = new User();
        validUser.setLogin("UserLogin6");
        validUser.setPassword("validP");
        validUser.setAge(20);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_lengthLoginSeven_ok() {
        User validUser = new User();
        validUser.setLogin("UserLogin7");
        validUser.setPassword("validPs");
        validUser.setAge(20);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_lengthLoginTen_ok() {
        User validUser = new User();
        validUser.setLogin("UserLogin10");
        validUser.setPassword("validPassw");
        validUser.setAge(20);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("UserNullPassword");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthPasswordZero_notOk() {
        User user = new User();
        user.setLogin("UserZeroPassword");
        user.setPassword("");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthPasswordThree_notOk() {
        User user = new User();
        user.setLogin("UserThreePassword");
        user.setPassword("abc");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthPasswordFive_notOk() {
        User user = new User();
        user.setLogin("UserFivePassword");
        user.setPassword("abcde");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_lengthPasswordSix_ok() {
        User validUser = new User();
        validUser.setLogin("UserLengthPassSix");
        validUser.setPassword("abcdef");
        validUser.setAge(20);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_lengthPasswordSeven_ok() {
        User validUser = new User();
        validUser.setLogin("UserLengthPassSeven");
        validUser.setPassword("abcdefg");
        validUser.setAge(20);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_LengthPasswordTen_ok() {
        User validUser = new User();
        validUser.setLogin("UserLengthPassTen");
        validUser.setPassword("abcdefghij");
        validUser.setAge(20);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("UserAgeNull");
        user.setPassword("password");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_negativeAgeSix_notOk() {
        User user = new User();
        user.setLogin("UserAgeNegative6");
        user.setPassword("password");
        user.setAge(-6);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_negativeAgeEighteen_notOk() {
        User user = new User();
        user.setLogin("UserAgeNegative18");
        user.setPassword("password");
        user.setAge(-18);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_negativeAgeSixty_notOk() {
        User user = new User();
        user.setLogin("UserAgeNegative60");
        user.setPassword("password");
        user.setAge(-60);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_userAgeZero_notOk() {
        User user = new User();
        user.setLogin("UserAge0");
        user.setPassword("password");
        user.setAge(0);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_userAgeSix_notOk() {
        User user = new User();
        user.setLogin("UserAge6");
        user.setPassword("password");
        user.setAge(6);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_userAgeSeventeen_notOk() {
        User user = new User();
        user.setLogin("UserAge17");
        user.setPassword("password");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_userAgeEighteen_ok() {
        User validUser = new User();
        validUser.setLogin("UserAge18");
        validUser.setPassword("validPassword");
        validUser.setAge(18);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_userAgeNineteen_ok() {
        User validUser = new User();
        validUser.setLogin("UserAge19");
        validUser.setPassword("validPassword");
        validUser.setAge(19);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_userAgeTwentyFive_ok() {
        User validUser = new User();
        validUser.setLogin("UserAge25");
        validUser.setPassword("validPassword");
        validUser.setAge(25);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    public void register_userAgeSixty_ok() {
        User validUser = new User();
        validUser.setLogin("UserAge60");
        validUser.setPassword("validPassword");
        validUser.setAge(60);

        assertTrue(Storage.people.add(validUser));
    }

    @Test
    void register_userWithMaxAge_ok() {
        User validUser = new User();
        validUser.setLogin("UserMaxAge");
        validUser.setPassword("validPassword");
        validUser.setAge(Integer.MAX_VALUE);

        registrationServiceImpl.register(validUser);

        assertNotNull(storageDao.get("UserMaxAge"));
    }

    @Test
    void register_userWithMinAge_notOk() {
        User user = new User();
        user.setLogin("UserMinAge");
        user.setPassword("validPassword");
        user.setAge(Integer.MIN_VALUE);

        assertThrows(RegistrationException.class, () -> registrationServiceImpl.register(user));
    }
}
