package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.CustomException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User userOne;
    private User userTwo;
    private User userThree;
    private User userFour;
    private User userFourClone;
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        userOne = new User(1238383L, "world12", "userworld123", 20);
        userTwo = new User(2323233L, "world13", "userworld1543", 25);
        userThree = new User(2323233L, "world14", "userworld95884", 19);
        userFour = new User(12333L, "world1266", "userw23023", 21);
        userFourClone = new User(12333L, "world1266", "userw23023", 21);
        Storage.people.clear();
    }

    @Test
    void addUser_Ok() throws CustomException {
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
        try {
            User userTestFour = registrationService.register(userFour);
            User userTestFouClone = registrationService.register(userFourClone);
        } catch (CustomException e) {
            return;
        }
        fail("You can not add users with same login");
    }

    @Test
    void addUserWithNullPassword_NotOk() {
        User userWithNullPassword = new User(12333L, "world126333", null, 21);
        try {
            registrationService.register(userWithNullPassword);
        } catch (CustomException e) {
            return;
        }
        fail("We can not add user with null password");
    }

    @Test
    void addUserWithNullLogin_NotOk() {
        User userWithNullLogin = new User(12333L, null, "jdhfhd83", 21);
        try {
            registrationService.register(userWithNullLogin);
        } catch (CustomException e) {
            return;
        }
        fail("We can not add user with null login");
    }

    @Test
    void addUserWithForbiddenAge_NotOk() {
        User user = new User(12333L, "newLogin1029", "jdhfhd83", 17);
        try {
            registrationService.register(user);
        } catch (CustomException e) {
            return;
        }
        fail("You can not add use with age under 18");
    }

    @Test
    void addUserWithForbiddenPassword_NotOk() {
        User user = new User(12333L, "newLogin10329", "2828", 17);
        try {
            registrationService.register(user);
        } catch (CustomException e) {
            return;
        }
        fail("You can not add user with password length less then 8");
    }
}
