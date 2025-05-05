package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiseTest {
    private static StorageDao dao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void getStorage() {
        dao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void createUsers() {
        user = new User();
        user.setLogin("FirstLogin");
        user.setPassword("password");
        user.setAge(20);
    }

    @Test
    public void register_NewUser_Ok() {
        registrationService.register(user);
        User actualUser = dao.get(user.getLogin());
        assertNotNull(actualUser, "DAO must return not null user in this case.");
        assertEquals(actualUser.getAge(), user.getAge(),
                "the expacted age value is " + user.getAge()
                        + ", but the age is " + actualUser.getAge());
        assertEquals(actualUser.getPassword(), user.getPassword(),
                "the expected password is " + user.getPassword()
                        + ", but the password is " + actualUser.getPassword());
    }

    @Test
    public void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "In case of null-User should throw RuntimeException.");
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "In case of null-name of User should throw RuntimeException.");
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "In case of null-password should throw RuntimeException.");
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "In case of null-age should throw RuntimeException.");
    }

    @Test
    public void register_ExistingUser_NotOk() {
        user.setLogin("second");
        User sameUser = registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameUser),
                "In case of the same User should throw RuntimeException.");
    }

    @Test
    public void register_TooYoungUser_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "In case of the User's age smaller 18 should throw RuntimeException.");
    }

    @Test
    void register_NegativeAge_NotOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "In case of negative age should throw RuntimeException.");
    }

    @Test
    void register_18AgeUser_Ok() {
        user.setAge(18);
        user.setLogin("18age");
        registrationService.register(user);
        User actualUser = dao.get(user.getLogin());
        assertEquals(actualUser.getAge(), 18,
                "Expected age is 18, but was " + actualUser.getAge());
    }

    @Test
    public void register_ShortPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "In case of too short password should throw RuntimeException.");
    }

    @Test
    public void register_6SymbolsOfPassword_Ok() {
        user.setPassword("123456");
        user.setLogin("6lettersPassword");
        registrationService.register(user);
        User actualUser = dao.get(user.getLogin());
        assertEquals(actualUser.getPassword(), user.getPassword(),
                "Expected password is 123456, but was " + actualUser.getPassword());
    }
}
