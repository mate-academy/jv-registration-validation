package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final String firstValidLogin = "Robert";
    private final String secondValidLogin = "genryyy";
    private final String validPassword = "qwerty123";
    private final String invalidLogin = "qwe";
    private final String invalidPassword = "qwert";
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
    void register_validUser_Ok() {
        User user = new User();
        user.setId(id);
        user.setPassword(validPassword);
        user.setLogin(firstValidLogin);
        user.setAge(validAge);
        registrationService.register(user);
    }

    @Test
    void register_twoValidUsers_Ok() {
        User user = new User();
        user.setId(id);
        user.setPassword(validPassword);
        user.setLogin(firstValidLogin);
        user.setAge(validAge);
        User secondValidUser = new User();
        secondValidUser.setAge(validAge);
        secondValidUser.setLogin(secondValidLogin);
        secondValidUser.setPassword(validPassword);
        registrationService.register(user);
        registrationService.register(secondValidUser);
        assertEquals(2, Storage.PEOPLE.size());
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_shortLogin_notOk() {
        User invalidUserByLoginLength = new User();
        invalidUserByLoginLength.setAge(19);
        invalidUserByLoginLength.setLogin(invalidLogin);
        invalidUserByLoginLength.setPassword(validPassword);
        invalidUserByLoginLength.setId(id++);
        assertThrows(RegistrationExseption.class,() -> registrationService
                .register(invalidUserByLoginLength));
    }

    @Test
    void invalidUserByPasswordLength_notOk() {
        User invalidUserByPasswordLength = new User();
        invalidUserByPasswordLength.setAge(19);
        invalidUserByPasswordLength.setLogin(firstValidLogin);
        invalidUserByPasswordLength.setPassword(invalidPassword);
        invalidUserByPasswordLength.setId(id++);
        assertThrows(RegistrationExseption.class,() -> registrationService
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
        assertThrows(RegistrationExseption.class,() -> registrationService.register(existUser));
    }

    @Test
    void checkIsUserAdult_NotOk() {
        User invalidUserByAge = new User();
        invalidUserByAge.setAge(17);
        invalidUserByAge.setLogin(secondValidLogin);
        invalidUserByAge.setPassword(validPassword);
        invalidUserByAge.setId(id++);
        assertThrows(RegistrationExseption.class,() -> registrationService
                .register(invalidUserByAge));
    }
}
