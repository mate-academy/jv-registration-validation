package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void passwordNotNull_Ok() {
        User userTest = new User();
        userTest.setId(4135141L);
        userTest.setLogin("bondorol");
        userTest.setPassword("1234567");
        userTest.setAge(43);
        assertNotNull(userTest.getPassword());
    }

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
        userSecondTest.setLogin("fsagah");

        String expected = userFirstTest.getLogin();
        String actual = userSecondTest.getLogin();

        assertNotEquals(expected, actual);
    }
}
