
package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private static final User user = new User();
    private static User user1;
    private static final User user2 = new User();

    @BeforeAll
    static void beforeAll() {
        user.setId(312312414L);
        user.setAge(22);
        user.setLogin("Boby_Roys");
        user.setPassword("A485sde");

        user2.setId(567893412L);
        user2.setAge(18);
        user2.setLogin("Lennox Lewis");
        user2.setPassword("12");

        registrationService.register(user);
        registrationService.register(user2);
    }

    @Test
    void user_Data_Is_Null_Is_NotOk() {
        boolean userNullCheck = registrationService.userNullCheck(user);
        assertFalse(userNullCheck);
    }

    @Test
    void user_Age_Is_Low_Test() {
        boolean actual = registrationService.userAgeCheck(user);
        assertTrue(actual);
    }

    @Test
    void userPasswordIs_Ok() {
        boolean actual = registrationService.userPasswordCheck(user);
        assertTrue(actual);
    }

    @Test
    void userPasswordIs_Not_Ok() {
        boolean actual = registrationService.userPasswordCheck(user2);
        assertFalse(actual);
    }

    @Test
    void userContainInData_Ok() {
        boolean actual = registrationService.loginUserCheck(user);
        assertFalse(actual);
    }
}
