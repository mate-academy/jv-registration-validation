package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.MyException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void uniqueUserName_Ok() {
        user.setLogin("Materia");
        user.setPassword("123456");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals("Materia", actual.getLogin());
    }

    @Test
    void theSameLoginInTheStorage_NotOk() {
        user.setLogin("American");
        user.setPassword("123456");
        user.setAge(18);
        registrationService.register(user);

        User user1 = new User();
        user1.setLogin("American");
        user1.setPassword("123456");
        user1.setAge(24);
        assertThrows(MyException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void loginLessThenSixCharacters_NotOk() {
        user.setLogin("Bob");
        user.setPassword("123456");
        user.setAge(18);
        assertThrows(MyException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordsLessThenSixCharacters_NotOk() {
        user.setLogin("Bobabu");
        user.setPassword("12345");
        user.setAge(18);
        assertThrows(MyException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageLessThenEighteen_NotOk() {
        user.setLogin("Bobabu");
        user.setPassword("123456");
        user.setAge(17);
        assertThrows(MyException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNull_NotOk() {
        user.setLogin("User12");
        user.setPassword("123456");
        assertThrows(MyException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin("User12");
        user.setPassword("123456");
        assertThrows(MyException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword("123456");
        assertThrows(MyException.class, () -> {
            registrationService.register(user);
        });
    }
}
