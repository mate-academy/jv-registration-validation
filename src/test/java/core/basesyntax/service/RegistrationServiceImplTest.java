package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User firstUserOk;
    private static User secondUserOk;
    private static User thirdUserOk;
    private static User userPasswordNotOk;
    private static User userAgeNotOk;
    private static User userNameNullNotOk;

    @BeforeClass
    public static void beforeClass() {
        registrationService = new RegistrationServiceImpl();
        firstUserOk = setUserData(1111L, "Bob", "qwerty", 28);
        secondUserOk = setUserData(2222L, "Alice", "asdfghjk", 29);
        thirdUserOk = setUserData(3333L, "Jhon", "zxcvbnm", 25);
        userPasswordNotOk = setUserData(-1111L, "BadBob", "qwer", 19);
        userAgeNotOk = setUserData(-2222L, "BadAlice", "asdfghjk", 17);
        userNameNullNotOk = setUserData(-2222L, null, "asdfghjk", 23);
    }

    @Before
    public void setUp() {
        Storage.people.clear();
    }

    @Test
    public void register_nullName_notOk() {
        registrationService.register(firstUserOk);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userNameNullNotOk);
        });
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_Password_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userPasswordNotOk);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        registrationService.register(firstUserOk);
        userPasswordNotOk = setUserData(-1111L, "BadBob", null, 19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userPasswordNotOk);
        });
    }

    @Test
    public void register_AgeLessThan18_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userAgeNotOk);
        });
    }

    @Test
    public void register_AgeLessThan0_notOk() {
        setUserData(-1111L, "BadBob", "qwer", -20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userAgeNotOk);
        });
    }

    @Test
    public void register_nullAge_notOk() {
        setUserData(-1111L, "BadBob", "qwer", null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userAgeNotOk);
        });
    }

    @Test
    public void register_SecondUserExists_notOk() {
        registrationService.register(firstUserOk);
        registrationService.register(secondUserOk);
        registrationService.register(thirdUserOk);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUserOk);
        });
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