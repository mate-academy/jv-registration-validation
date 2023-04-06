package core.basesyntax.service;

import core.basesyntax.model.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void userNotNull_Ok() {
        User userTest = new User();
        userTest.setId(31654L);
        userTest.setLogin("Bond-1234");
        userTest.setPassword("1234567");
        userTest.setAge(43);
        User register = registrationService.register(userTest);
        assertNotNull(register.getLogin());
    }

    @Test
    void passwordNotNull_Ok() {
        User userTest = new User();
        userTest.setPassword("1234567");
        assertNotNull(userTest.getPassword());
    }


    @Test
    void idNotNull_Ok() {
        User userTest = new User();
        userTest.setId(3264827L);
        assertNotNull(userTest.getId());
    }

    @Test
    void ageIsNotNull_Ok() {
        User userTest = new User();
        userTest.setAge(20);
        assertNotNull(userTest.getAge());
    }

    @Test
    void loginIsNotNull_Ok() {
        User userTest = new User();
        userTest.setLogin("ewruyeu");
        assertNotNull(userTest.getLogin());
    }

    @Test
    void theLoginIsEquals_notOk() {
        User userFirstTest = new User();
        User userSecondTest = new User();
        userFirstTest.setLogin("bondorol");
        userSecondTest.setLogin("askdfa");

        String expected = userFirstTest.getLogin();
        String actual = userSecondTest.getLogin();

        assertNotEquals(expected, actual);
    }

    @Test
    void thePasswordLengthIsThreeCharacters_notOk() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("123");
        secondUserTest.setPassword("123456");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertNotEquals(expected, actual);
    }

    @Test
    void thePasswordLengthIsFiveCharacters_notOk() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("12345");
        secondUserTest.setPassword("123456");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertNotEquals(expected, actual);
    }

    @Test
    void thePasswordLengthIsSixCharacters_ok() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("123456");
        secondUserTest.setPassword("567890");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertEquals(expected, actual);
    }

    @Test
    void thePasswordLengthIsEightCharacters_ok() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("12345678");
        secondUserTest.setPassword("567890");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertNotEquals(expected, actual);
    }
}
