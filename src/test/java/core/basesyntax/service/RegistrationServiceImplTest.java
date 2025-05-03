package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    RegistrationServiceImpl service = new RegistrationServiceImpl();
    User validUser;
    private final String validLogin = "valid_login@savelogin.com";
    private final String validOtherLogin = "other_login@savelogin.com";
    private final String validPassword = "sTroN_G_pass*rdo";
    private final String invalidPassword = "12345";
    private final String invalidLogin = "simpl";
    int validAge = 25;
    private final int underLimitAge = 14;

    @BeforeEach
    void setUp() {
        User validUserInfo = new User(validLogin, validPassword, validAge);
        service = new RegistrationServiceImpl();
        validUser = validUserInfo;
        service.register(validUserInfo);
    }

    @Test
    void checkNotExistingElement() {
        assertNull(storageDao.get("NOT_EXISTING_LOGIN"));;
    }

    @Test
    void checkInvalidLogin() {
        assertNull(storageDao.get(validOtherLogin));;
    }

    @Test
    void loginExist() {
        assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "User with this login already exist");
    }

    @Test
    void passwordShort() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(new User(validOtherLogin, invalidPassword, validAge)),
                "User password is week - should be at least 6 characters long");
    }

    @Test
    void emailShort() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(new User(invalidLogin, validOtherLogin, validAge)),
                "User login should be at least 6 characters long");
    }

    @Test
    void ageBelowLimit() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(new User(validOtherLogin, validPassword, underLimitAge)),
                "Minimal user age is 18 years");
    }

    @Test
    void userLoginAbsent() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(new User(null, validPassword, validAge)),
                "User login not provided");
    }

    @Test
    void userPasswordAbsent() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(new User(validOtherLogin, null, validAge)),
                "User password not provided");
    }

    @Test
    void negativeAge() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(new User(validOtherLogin, validPassword, -validAge)),
                "User age must be positive number");
    }

    @Test
    void getRegisteredUser() {
        assertEquals(storageDao.get(validLogin), validUser);
        User userOne = new User(validOtherLogin, validPassword, validAge);
        service.register(userOne);
        assertEquals(storageDao.get(validOtherLogin), userOne);
    }

    @AfterEach
    void deleteUser() {
        storageDao.delete(validOtherLogin);
        storageDao.delete(validLogin);
    }
}