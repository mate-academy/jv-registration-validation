package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User firstUserOk;
    private static User secondUserOk;
    private static User thirdUserOk;
    private static User userPasswordNotOk;
    private static User userAgeNotOk;
    private static User userLoginNullNotOk;

    @BeforeClass
    public static void beforeClass() {
        registrationService = new RegistrationServiceImpl();
        firstUserOk = setUserData(1111L, "Bob", "qwerty", 18);
        secondUserOk = setUserData(2222L, "Alice", "asdfghjk", 29);
        thirdUserOk = setUserData(3333L, "Jhon", "zxcvbnm", 25);
        userPasswordNotOk = setUserData(-1111L, "BadBob", "qwer", 19);
        userAgeNotOk = setUserData(-2222L, "BadAlice", "asdfghjk", 17);
        userLoginNullNotOk = setUserData(-2222L, null, "asdfghjk", 23);
    }

    @Before
    public void setUp() {
        Storage.people.clear();
    }

    @Test
    public void register_nullLogin_notOk() {
        registrationService.register(firstUserOk);
        assertThrows(RuntimeException.class, () -> registrationService.register(userLoginNullNotOk));
    }

    @Test
    public void register_emptyLogin_notOk() {
        userLoginNullNotOk = setUserData(-2222L, "", "asdfghjk", 23);
        assertThrows(RuntimeException.class, () -> registrationService.register(userLoginNullNotOk));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_passwordLessThanMinLength_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userPasswordNotOk));
    }

    @Test
    public void register_nullPassword_notOk() {
        registrationService.register(firstUserOk);
        userPasswordNotOk = setUserData(-1111L, "BadBob", null, 19);
        assertThrows(RuntimeException.class, () -> registrationService.register(userPasswordNotOk));
    }

    @Test
    public void register_AgeLessThanMinAge_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userAgeNotOk));
    }

    @Test
    public void register_nullAge_notOk() {
        userAgeNotOk = setUserData(-1111L, "BadBob", "qwer789", null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userAgeNotOk));
    }

    @Test
    public void register_SecondUserExists_notOk() {
        registrationService.register(firstUserOk);
        registrationService.register(secondUserOk);
        registrationService.register(thirdUserOk);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUserOk));
    }

    @Test
    public void register_ThreeUsers_Ok() {
        int expect = 3;
        registrationService.register(firstUserOk);
        registrationService.register(secondUserOk);
        registrationService.register(thirdUserOk);
        int actual = Storage.people.size();
        assertEquals(expect, actual);
    }

    private static User setUserData(Long id, String login, String password, Integer age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
