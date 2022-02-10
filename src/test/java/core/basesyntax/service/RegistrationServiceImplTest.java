package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user = new User();
    private User userWithTheSameLogin1 = new User();
    private User userWithTheSameLogin2 = new User();

    @BeforeEach
    void setUp() {
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(18);
    }

    @Test
    void register_allDataCorrect_ok() {
        User testUser = registrationService.register(user);
        assertEquals(user, testUser);
    }

    @Test
    void register_less6Characters_notOk() {
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_lessThan18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_more100_notOk() {
        user.setAge(101);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_UserNull_notOK() {
        User user2 = null;
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordSpaces_notOk() {
        user.setPassword("qw er ty");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("        ");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullInLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void registerSameLoginInStorage_notOk() {
        userWithTheSameLogin1.setLogin("loginOne");
        userWithTheSameLogin1.setPassword("password1");
        userWithTheSameLogin1.setAge(20);
        userWithTheSameLogin2.setLogin("loginOne");
        userWithTheSameLogin2.setPassword("password2");
        userWithTheSameLogin2.setAge(23);
        storageDao.add(userWithTheSameLogin1);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithTheSameLogin2)
        );
    }
}
