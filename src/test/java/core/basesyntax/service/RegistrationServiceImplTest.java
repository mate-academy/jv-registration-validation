package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void user_NotNull_Ok() {
        User userTest = new User();
        userTest.setLogin("bondorol");
        userTest.setPassword("123456");
        userTest.setId(12365L);
        userTest.setAge(18);
        User register = registrationService.register(userTest);
        assertNotNull(register);
    }

    @Test
    void password_Null_notOk() {
        User userTest = new User();
        userTest.setLogin("bondorol");
        userTest.setPassword(null);
        userTest.setId(12365L);
        userTest.setAge(18);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void user_IsNull_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void age_Is0_notOk() {
        int actual = 18;
        User userTest = new User();
        userTest.setAge(0);
        Integer expected = userTest.getAge();
        assertNotEquals(expected, actual);
    }

    @Test
    void age_Is5_notOk() {
        int actual = 18;
        User userTest = new User();
        userTest.setAge(5);
        Integer expected = userTest.getAge();
        assertNotEquals(expected, actual);
    }

    @Test
    void age_Is10_notOk() {
        int actual = 18;
        User userTest = new User();
        userTest.setAge(10);
        Integer expected = userTest.getAge();
        assertNotEquals(expected, actual);
    }

    @Test
    void age_Is18_ok() {
        int actual = 18;
        User userTest = new User();
        userTest.setAge(18);
        Integer expected = userTest.getAge();
        assertEquals(expected, actual);
    }

    @Test
    void age_IsMore_Than18_ok() {
        int actual = 18;
        User userTest = new User();
        userTest.setAge(19);
        Integer expected = userTest.getAge();
        assertNotEquals(expected, actual);
    }

    @Test
    void login_IsNotNull_Ok() {
        User userTest = new User();
        userTest.setLogin("ewruyeu");
        assertNotNull(userTest.getLogin());
    }

    @Test
    void login_IsEquals_notOk() {
        User userFirstTest = new User();
        User userSecondTest = new User();
        userFirstTest.setLogin("bondorol");
        userSecondTest.setLogin("wqeqerq");

        String expected = userFirstTest.getLogin();
        String actual = userSecondTest.getLogin();

        assertNotEquals(expected, actual);
    }

    @Test
    void passwordLength_less_thanSix_notOk() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("123");
        secondUserTest.setPassword("123456");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertNotEquals(expected, actual);
    }

    @Test
    void passwordLength_IsFiveCharacters_notOk() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("12345");
        secondUserTest.setPassword("123456");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertNotEquals(expected, actual);
    }

    @Test
    void passwordLength_IsSixCharacters_ok() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("123456");
        secondUserTest.setPassword("567890");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertEquals(expected, actual);
    }

    @Test
    void passwordLength_IsEightCharacters_ok() {
        User firstUserTest = new User();
        User secondUserTest = new User();
        firstUserTest.setPassword("12345678");
        secondUserTest.setPassword("567890");
        int expected = firstUserTest.getPassword().length();
        int actual = secondUserTest.getPassword().length();
        assertNotEquals(expected, actual);
    }
}
