package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final String firstValidLogin = "Username";
    private final String secondValidLogin = "Username2";
    private final String validPassword = "Password";
    private final String invalidLogin = "12345";
    private final String invalidPassword = "12345";
    private final int validAge = 18;
    private long id;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void userRegistration_Ok() {
        User validUser = new User();
        validUser.setId(id);
        validUser.setPassword(validPassword);
        validUser.setLogin(firstValidLogin);
        validUser.setAge(validAge);
        registrationService.register(validUser);
    }

    @Test
    void sizeStorageWithTwoCorrectUsers_Ok() {
        User validUser = new User();
        validUser.setId(id);
        validUser.setPassword(validPassword);
        validUser.setLogin(firstValidLogin);
        validUser.setAge(validAge);
        User secondValidUser = new User();
        secondValidUser.setAge(validAge);
        secondValidUser.setLogin(secondValidLogin);
        secondValidUser.setPassword(validPassword);
        secondValidUser.setId(id++);
        registrationService.register(validUser);
        registrationService.register(secondValidUser);
        assertEquals(2, Storage.PEOPLE.size());
    }

    @Test
    void getUserByID_Ok() {
        User validUser = new User();
        validUser.setId(id);
        validUser.setPassword(validPassword);
        validUser.setLogin(firstValidLogin);
        validUser.setAge(validAge);
        registrationService.register(validUser);
        Assertions.assertEquals(validUser,Storage.PEOPLE.get(0));
    }

    @Test
    void checkUserIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void checkIsUserLoginLength_notOk() {
        User invalidUserByLoginLength = new User();
        invalidUserByLoginLength.setAge(19);
        invalidUserByLoginLength.setLogin(invalidLogin);
        invalidUserByLoginLength.setPassword(validPassword);
        invalidUserByLoginLength.setId(id++);
        assertThrows(ValidationException.class,() -> registrationService
                .register(invalidUserByLoginLength));
    }

    @Test
    void invalidUserByPasswordLength_notOk() {
        User invalidUserByPasswordLength = new User();
        invalidUserByPasswordLength.setAge(19);
        invalidUserByPasswordLength.setLogin(firstValidLogin);
        invalidUserByPasswordLength.setPassword(invalidPassword);
        invalidUserByPasswordLength.setId(id++);
        assertThrows(ValidationException.class,() -> registrationService
                .register(invalidUserByPasswordLength));
    }

    @Test
    void userWithTheSameLoginInStorage_NotOk() {
        User validUser = new User();
        validUser.setId(id);
        validUser.setPassword(validPassword);
        validUser.setLogin(firstValidLogin);
        validUser.setAge(validAge);
        registrationService.register(validUser);
        User existUser = new User();
        existUser.setId(id);
        existUser.setPassword(validPassword);
        existUser.setLogin(firstValidLogin);
        existUser.setAge(validAge);
        assertThrows(ValidationException.class,() -> registrationService.register(existUser));
    }

    @Test
    void checkIsUserAdult_NotOk() {
        User invalidUserByAge = new User();
        invalidUserByAge.setAge(17);
        invalidUserByAge.setLogin(secondValidLogin);
        invalidUserByAge.setPassword(validPassword);
        invalidUserByAge.setId(id++);
        assertThrows(ValidationException.class,() -> registrationService
                .register(invalidUserByAge));
    }
}
