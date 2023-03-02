package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private User userOne;
    private User userTwo;
    private User userThree;
    private User userFour;
    private User user;
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        userOne = new User(1238383L, "world12", "userworld123", 20);
        userTwo = new User(2323233L, "world13", "userworld1543", 25);
        userThree = new User(2323233L, "world14", "userworld95884", 19);
        userFour = new User(12333L, "world1266", "userw23023", 21);
        Storage.people.clear();
    }

    @Test
    void addUser_Ok() {
        User userTestOne = registrationService.register(userOne);
        User userTestTwo = registrationService.register(userTwo);
        User userTestThree = registrationService.register(userThree);
        assertEquals(userOne, userTestOne);
        assertEquals(userTwo, userTestTwo);
        assertEquals(userThree, userTestThree);
        assertEquals(3, Storage.people.size());
    }

    @Test
    void addExistUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            StorageDaoImpl storageDao = new StorageDaoImpl();
            storageDao.add(userFour);
            registrationService.register(userFour);
        }, "You can not add users with same login");
    }

    @Test
    void addUserWithNullPassword_NotOk() {
        User userWithNullPassword = new User(12333L, "world126333", null, 21);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullPassword);
        }, "We can not add user with null password");
    }

    @Test
    void addUserWithNullLogin_NotOk() {
        User userWithNullLogin = new User(12333L, null, "jdhfhd83", 21);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullLogin);
        }, "We can not add user with null login");
    }

    @Test
    void addUserWithForbiddenAge_NotOk() {
        User user = new User(12333L, "newLogin1029", "jdhfhd83", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "You can not add use with age under 18");
    }

    @Test
    void addUserWithForbiddenPassword_NotOk() {
        User user = new User(12333L, "newLogin10329", "2828", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, "You can not add user with password length less then 8");
    }

    @Test
    void addUserWithNullAge_NotOk() {
        User user = new User(12333L, "newLogin10329", "2828333333", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        }, " You can not add user with null age");
    }
}
