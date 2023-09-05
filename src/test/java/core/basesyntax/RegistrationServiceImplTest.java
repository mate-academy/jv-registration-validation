package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String FIRST_VALID_LOGIN = "Username";
    private static final String SECOND_VALID_LOGIN = "Username2";
    private static final String VALID_PASSWORD = "Password";
    private static final String INVALID_LOGIN = "12345";
    private static final String INVALID_PASSWORD = "12345";
    private static final int VALID_AGE = 18;
    private static long id;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User validUser = new User();
    private User existUser = validUser;

    @BeforeEach
    void beforeEach() {
        validUser.setAge(VALID_AGE);
        validUser.setLogin(FIRST_VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setId(id++);
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
        User secondValidUser = new User();
        secondValidUser.setAge(VALID_AGE);
        secondValidUser.setLogin(SECOND_VALID_LOGIN);
        secondValidUser.setPassword(VALID_PASSWORD);
        secondValidUser.setId(id++);
        registrationService.register(validUser);
        registrationService.register(secondValidUser);
        assertEquals(2, Storage.PEOPLE.size());
    }

    @Test
    void checkIsUserLoginLength_notOk() {
        User invalidUserByLoginLength = new User();
        invalidUserByLoginLength.setAge(19);
        invalidUserByLoginLength.setLogin(INVALID_LOGIN);
        invalidUserByLoginLength.setPassword(VALID_PASSWORD);
        invalidUserByLoginLength.setId(id++);
        assertThrows(ValidationException.class,() -> registrationService
                .register(invalidUserByLoginLength));
    }

    @Test
    void invalidUserLoginLength_notOk() {
        User invalidUserByPasswordLength = new User();
        invalidUserByPasswordLength.setAge(19);
        invalidUserByPasswordLength.setLogin(FIRST_VALID_LOGIN);
        invalidUserByPasswordLength.setPassword(INVALID_PASSWORD);
        invalidUserByPasswordLength.setId(id++);
        assertThrows(ValidationException.class,() -> registrationService
                .register(invalidUserByPasswordLength));
    }

    @Test
    void checkIsUserAdult_NotOk() {
        User invalidUserByAge = new User();
        invalidUserByAge.setAge(17);
        invalidUserByAge.setLogin(SECOND_VALID_LOGIN);
        invalidUserByAge.setPassword(VALID_PASSWORD);
        invalidUserByAge.setId(id++);
        assertThrows(ValidationException.class,() -> registrationService
                .register(invalidUserByAge));
    }

    @Test
    void checkUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void userWithTheSameLoginInStorage_NotOk() {
        registrationService.register(validUser);
        assertThrows(ValidationException.class,() -> registrationService.register(existUser));
    }
}
