package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void addExistUser_NotOK() {
        User actual = new User();
        actual.setLogin("abcdef");
        actual.setPassword("123456");
        actual.setAge(18);
        User user = registrationService.register(actual);
        try {
            User userCopy = registrationService.register(actual);
        } catch (NotValidRegistrationExeption e) {
            return;
        }
        fail("Error! Shouldnt be able too add same user");
    }

    private void fail(String s) {
    }

    void addUserWithSameLogin_NotOK() {
        User actual = new User();
        actual.setLogin("abcdef");
        actual.setPassword("123456");
        actual.setAge(18);
        User actualCopy = new User();
        actualCopy.setLogin("abcdef");
        actualCopy.setPassword("123457");
        actualCopy.setAge(18);
        User user = registrationService.register(actual);
        try {
            User userCopy = registrationService.register(actualCopy);
        } catch (NotValidRegistrationExeption e) {
            return;
        }
        fail("Error! Shouldnt be able too add user with same login");
    }

    @Test
    void userLoginLess6Char_OK() {
        User actual = new User();
        actual.setLogin("abcd");
        actual.setPassword("123456");
        actual.setAge(18);
        try {
            User user = registrationService.register(actual);
        } catch (NotValidRegistrationExeption e) {
            return;
        }
        fail("Error! Can't add user with less 6 char login");
    }

    @Test
    void userPasswordLess6Char_OK() {
        User actual = new User();
        actual.setLogin("abcdef");
        actual.setPassword("1234");
        actual.setAge(18);
        try {
            User user = registrationService.register(actual);
        } catch (NotValidRegistrationExeption e) {
            return;
        }
        fail("Error! Can't add user with less 6 char password");
    }

    @Test
    void userUserAgeLess18Char_OK() {
        User actual = new User();
        actual.setLogin("abcdef");
        actual.setPassword("1234");
        actual.setAge(13);
        try {
            User user = registrationService.register(actual);
        } catch (NotValidRegistrationExeption e) {
            return;
        }
        fail("Error! NotValidRegistration should be thrown");
    }

    @Test
    void addNullUser_notOK() {
        User actual = null;
        User user = registrationService.register(actual);
        assertEquals(user, null, "Error! User should be null");
    }
}
