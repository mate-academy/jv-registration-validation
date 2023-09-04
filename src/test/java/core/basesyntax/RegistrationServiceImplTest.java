package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Username";
    private static final String ONE_MORE_LOGIN = "Username2";
    private static final String VALID_PASSWORD = "Password";
    private static final String UNVALID_LOGIN = "12345";
    private static final String UNVALID_PASSWORD = "12345";
    private static final int VALID_AGE = 18;
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static User validUser = new User();
    private static User existUser = validUser;

    @BeforeEach
    void beforeEach() {
        validUser.setAge(VALID_AGE);
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setId((long) 12);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void userRegistration_Ok() {
        registrationService.register(validUser);
    }

    @Test
    void sizeStorageWithTwoCorrectUsers_Ok() {
        User validUser2 = new User();
        validUser2.setAge(VALID_AGE);
        validUser2.setLogin(ONE_MORE_LOGIN);
        validUser2.setPassword(VALID_PASSWORD);
        validUser2.setId((long) 13);
        registrationService.register(validUser2);
        registrationService.register(validUser);
        assertEquals(2, Storage.PEOPLE.size());
    }

    @Test
    void checkIsUserLoginLength_notOk() {
        User unvalidUserLogin = new User();
        unvalidUserLogin.setAge(19);
        unvalidUserLogin.setLogin(UNVALID_LOGIN);
        unvalidUserLogin.setPassword(VALID_PASSWORD);
        unvalidUserLogin.setId((long) 1);
        assertThrows(RuntimeException.class,() -> registrationService
                .register(unvalidUserLogin));
    }

    @Test
    void checkIsUserPasswordLength_NotOk() {
        User unvalidUserPassword = new User();
        unvalidUserPassword.setAge(19);
        unvalidUserPassword.setLogin(VALID_LOGIN);
        unvalidUserPassword.setPassword(UNVALID_PASSWORD);
        unvalidUserPassword.setId((long) 1);
        assertThrows(RuntimeException.class,() -> registrationService
                .register(unvalidUserPassword));
    }

    @Test
    void checkIsUserAdult_NotOk() {
        User unvalidUser1 = new User();
        unvalidUser1.setAge(17);
        unvalidUser1.setLogin(ONE_MORE_LOGIN);
        unvalidUser1.setPassword(VALID_PASSWORD);
        unvalidUser1.setId((long) 17);
        assertThrows(RuntimeException.class,() -> registrationService.register(unvalidUser1));
    }

    @Test
    void checkUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void userWithTheSameLoginInStorage_NotOk() {
        registrationService.register(validUser);
        assertThrows(RuntimeException.class,() -> registrationService.register(existUser));
    }
}
