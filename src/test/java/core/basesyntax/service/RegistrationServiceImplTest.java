package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    StorageDao storageDao = new StorageDaoImpl();
    RegistrationServiceImpl service = new RegistrationServiceImpl();
    User validUser;
    String VALID_LOGIN_1 = "valid_login@savelogin.com";
    String VALID_LOGIN_2 = "other_login@savelogin.com";
    String VALID_PASSWORD = "sTroN_G_pass*rdo";
    String INVALID_PASSWORD = "12345";
    String INVALID_LOGIN = "simpl";
    int AGE_VALID = 25;
    int UNDER_LIMIT_AGE = 14;
    @BeforeEach
    void setUp() {
        User validUserInfo = new User(VALID_LOGIN_1, VALID_PASSWORD, AGE_VALID);
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
        assertNull(storageDao.get(VALID_LOGIN_2));;
    }

    @Test
    void loginExist() {
        assertThrows(InvalidUserDataException.class, () -> service.register(validUser),
                "User with this login already exist");
    }

    @Test
    void passwordShort() {
        assertThrows(InvalidUserDataException.class, () -> service.register(new User(VALID_LOGIN_2, INVALID_PASSWORD, AGE_VALID)),
                "User password is week - should be at least 6 characters long");
    }

    @Test
    void emailShort() {
        assertThrows(InvalidUserDataException.class, () -> service.register(new User(INVALID_LOGIN, VALID_LOGIN_2, AGE_VALID)),
                "User login should be at least 6 characters long");
    }

    @Test
    void ageBelowLimit() {
        assertThrows(InvalidUserDataException.class, () -> service.register(new User(VALID_LOGIN_2, VALID_PASSWORD, UNDER_LIMIT_AGE)),
                "Minimal user age is 18 years");
    }

    @Test
    void userLoginAbsent() {
        assertThrows(NullPointerException.class, () -> service.register(new User(null, VALID_PASSWORD, AGE_VALID)),
                "User login not provided");
    }

    @Test
    void userPasswordAbsent() {
        assertThrows(NullPointerException.class, () -> service.register(new User(VALID_LOGIN_2, null, AGE_VALID)),
                "User password not provided");
    }

    @Test
    void negativeAge() {
        assertThrows(InvalidUserDataException.class, () -> service.register(new User(VALID_LOGIN_2, VALID_PASSWORD, -AGE_VALID)),
                "User age must be positive number");
    }

    @Test
    void getRegisteredUser() {
        assertEquals(storageDao.get(VALID_LOGIN_1), validUser);
        User userOne = new User(VALID_LOGIN_2, VALID_PASSWORD, AGE_VALID);
        service.register(userOne);
        assertEquals(storageDao.get(VALID_LOGIN_2),  userOne);
    }

    @AfterEach
    void deleteUser() {
        storageDao.deleteUser(VALID_LOGIN_2);
        storageDao.deleteUser(VALID_LOGIN_1);
    }
}