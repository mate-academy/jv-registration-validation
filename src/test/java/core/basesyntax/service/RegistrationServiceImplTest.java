
package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final int MIN_CHAR_FOR_PASS = 6;
    private static final int MIN_AGE_FOR_REG = 18;

    private final RegistrationService registrationService
            = new RegistrationServiceImpl();

    private User userIsFine;
    private User userSecondWrongAge;
    private User userNull;
    private User userThirdCopyLoginFirstUser;
    private User userWithOverZeroAge;
    private User userWithWrongPassword;

    @BeforeEach
    void setUp() {
        userIsFine = new User();
        userSecondWrongAge = new User();
        userThirdCopyLoginFirstUser = new User();
        userWithOverZeroAge = new User();
        userWithWrongPassword = new User();

        userIsFine.setAge(22);
        userIsFine.setLogin("Boby_Roys");
        userIsFine.setPassword("A485sde");

        userSecondWrongAge.setAge(17);
        userSecondWrongAge.setLogin("Lennox Lewis");
        userSecondWrongAge.setPassword("12asdf518ad");

        userThirdCopyLoginFirstUser.setAge(19);
        userThirdCopyLoginFirstUser.setLogin("Boby_Roys");
        userThirdCopyLoginFirstUser.setPassword("1524846");

        userThirdCopyLoginFirstUser.setAge(-1);
        userThirdCopyLoginFirstUser.setLogin("Colin Farel");
        userThirdCopyLoginFirstUser.setPassword("KolyaFarnitura");

        userWithWrongPassword.setAge(22);
        userWithWrongPassword.setLogin("Bob2y_Roys");
        userWithWrongPassword.setPassword("as");
    }

    @Test
    void user_Is_Ok() {
        assertEquals(userIsFine, registrationService.register(userIsFine));
    }

    @Test
    void user_Data_Is_Null_Is_NotOk() {
        assertNull(registrationService.register(userNull));
    }

    @Test
    void user_Age_Is_NotOk() {
        assertNull(registrationService.register(userSecondWrongAge));
    }

    @Test
    void userPasswordIs_Not_Ok() {
        assertNull(registrationService.register(userWithWrongPassword));
    }

    @Test
    void user_Storage_Check_Login_NotOk() {
        assertNull(registrationService.register(userThirdCopyLoginFirstUser));
    }
}
