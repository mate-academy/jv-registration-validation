package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static RegistrationService registrationServise;
    private static User firstUser;
    private static User secondUser;

    @BeforeAll
    static void beforeAll() {
        registrationServise = new RegistrationServiceImpl();
        firstUser = new User();
        secondUser = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        firstUser.setLogin("user");
        firstUser.setPassword("password");
        firstUser.setAge(21);
        secondUser.setLogin("1abcdef");
        secondUser.setPassword("password");
        secondUser.setAge(20);
    }

    @Test
    void register_correctUser_isOk() {
        assertNotNull(registrationServise.register(firstUser));
    }

    @Test
    void register_loginStartWithNumeric_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(secondUser));
    }

    @Test
    void register_loginLengthLessThenThree_notOk() {
        secondUser.setLogin("al");
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(secondUser));
    }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(firstUser));
    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(firstUser));
    }

    @Test
    void register_nullPassword_notOk() {
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(firstUser));
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        firstUser.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(firstUser));
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        firstUser.setPassword("abcde");
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(firstUser));
    }

    @Test
    void register_theSameUser_notOk() {
        registrationServise.register(firstUser);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(firstUser));
    }

    @Test
    void register_UserWithExistingLogin_notOk() {
        registrationServise.register(firstUser);
        secondUser.setLogin("user");
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(secondUser));
    }
}
