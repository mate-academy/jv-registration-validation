package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private final User firstValidUser = new User();
    private final User secondValidUser = new User();
    private final User thirdValidUser = new User();
    private final User sameFirstUser = new User();
    private final User nullAgeUser = new User();
    private final User nullLoginUser = new User();
    private final User nullPasswordUser = new User();
    private final User negativeAgeUser = new User();
    private final User incorrectPasswordUser = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstValidUser.setLogin("FirstUser");
        firstValidUser.setAge(18);
        firstValidUser.setPassword("Correct pass");
        secondValidUser.setLogin("SecondUser");
        secondValidUser.setAge(19);
        secondValidUser.setPassword("Correct password");
        thirdValidUser.setLogin("Third User");
        thirdValidUser.setAge(20);
        thirdValidUser.setPassword("Password");
        sameFirstUser.setLogin("FirstUser");
        sameFirstUser.setAge(18);
        sameFirstUser.setPassword("Correct pass");
        nullAgeUser.setLogin("Null age user");
        nullAgeUser.setAge(null);
        nullAgeUser.setPassword("Correct pass");
        nullLoginUser.setLogin(null);
        nullLoginUser.setAge(20);
        nullLoginUser.setPassword("Correct pass");
        nullPasswordUser.setLogin("Null password user");
        nullPasswordUser.setAge(19);
        nullPasswordUser.setPassword(null);
        negativeAgeUser.setLogin("Negative age user");
        negativeAgeUser.setAge(-19);
        negativeAgeUser.setPassword("Correct Password");
        incorrectPasswordUser.setLogin("Incorrect password user");
        incorrectPasswordUser.setAge(20);
        incorrectPasswordUser.setPassword("pass");
    }

    @Test
    void register_newUserValid_ok() {
        User actualUser = registrationService.register(firstValidUser);
        assertEquals(firstValidUser, actualUser, "The first valid user must be registered");
        actualUser = registrationService.register(secondValidUser);
        assertEquals(secondValidUser, actualUser, "The second valid user must be registered");
        actualUser = registrationService.register(thirdValidUser);
        assertEquals(thirdValidUser, actualUser, "The third valid user must be registered");
        int expectedSize = 3;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_sameUser_notOk() {
        registrationService.register(firstValidUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameFirstUser));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(nullPasswordUser));
    }

    @Test
    public void register_negativeAge_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(negativeAgeUser));
    }

    @Test
    public void register_incorrectPassword_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(incorrectPasswordUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
