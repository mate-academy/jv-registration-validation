package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final User user = new User();

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
    void register_passwordLessThan6Characters_notOk() {
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
    void register_ageLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageMoreThan60_notOk() {
        user.setAge(101);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void registerSameLoginInStorage_notOk() {
        final User firstUser = new User();
        final User secondUser = new User();
        firstUser.setLogin("loginOne");
        firstUser.setPassword("password1");
        firstUser.setAge(20);
        secondUser.setLogin("loginOne");
        secondUser.setPassword("password2");
        secondUser.setAge(23);
        storageDao.add(firstUser);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(secondUser)
        );
    }
}
