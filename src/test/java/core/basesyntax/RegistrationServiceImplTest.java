package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {

    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    public void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("TryMyCustomLogin");
        user.setPassword("1234567");
        user.setAge(20);

        storageDao.add(user);

        User newUser = new User();
        newUser.setLogin("TryMyCustomLogin");
        newUser.setPassword("12345678");
        newUser.setAge(21);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(newUser);
        });

    }

    @Test
    public void login_leangh_noOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("12345678");
        user.setAge(82);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void password_leangh_noOk() {
        User user = new User();
        user.setLogin("SuperLongLogin");
        user.setPassword("short");
        user.setAge(20);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_age_young_noOk() {
        User user = new User();
        user.setLogin("SuperLogin");
        user.setPassword("LongPassword");
        user.setAge(17);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_age_login_noOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("LongPassword");
        user.setAge(3);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_age_password_noOk() {
        User user = new User();
        user.setLogin("LongLogin");
        user.setPassword("abc");
        user.setAge(5);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_login_noNull_noOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("someLongPassword");
        user.setAge(56);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_password_noNull_noOk() {
        User user = new User();
        user.setLogin("SomeFancyLogin");
        user.setPassword(null);
        user.setAge(56);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_age_noNull_noOk() {
        User user = new User();
        user.setLogin("SuperExoticLogin");
        user.setPassword("SuperExoticPassword");
        user.setAge(null);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_login_has_whitespace_noOk() {
        User user = new User();
        user.setLogin("SomeLogin have whitespace");
        user.setPassword("SuperExoticPassword");
        user.setAge(63);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void user_password_has_whitespace_noOk() {
        User user = new User();
        user.setLogin("SomeLoginNoWhiteSpace");
        user.setPassword("Super ExoticPassword");
        user.setAge(63);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }
}