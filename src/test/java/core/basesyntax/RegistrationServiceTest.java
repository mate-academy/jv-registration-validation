package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl(storageDao);

    @BeforeEach
    public void setUpUser() {
        Storage.people.clear();
        user.setLogin("admin555");
        user.setPassword("qwert12345");
        user.setAge(33);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(null),
                "User should not be null!"
        );
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "Login should not be null!"
        );
    }

    @Test
    public void register_shortLogin_notOk() {
        user.setLogin("qwert");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "Login length should be more than 6 characters!"
        );
    }

    @Test
    public void register_borderLogin_ok() {
        user.setPassword("qwert1");
        boolean actual = registrationService.register(user).getPassword().length() == 6;
        assertTrue(actual, "Login should be more than 6 characters!");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "Password should not be null!"
        );
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setPassword("admin");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "Password length should be more than 6 characters!"
        );
    }

    @Test
    public void register_borderPassword_ok() {
        user.setPassword("admin1");
        boolean actual = registrationService.register(user).getPassword().length() == 6;
        assertTrue(actual, "Your password must be more than 6 characters long!");
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "Age should not be null!"
        );
    }

    @Test
    public void register_underAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "Your age is lower than 18."
        );
    }

    @Test
    public void register_borderAge_ok() {
        user.setAge(18);
        boolean actual = registrationService.register(user).getAge() == 18;
        assertTrue(actual, "Your age is lower than 18.");
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("admin1");
        user1.setPassword("qwert1234");
        user1.setAge(31);
        storageDao.add(user1);
        user.setLogin("admin1");
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(user),
                "This login already exists!"
        );
    }

    @Test
    public void register_validUser_ok() {
        User user1 = new User();
        user1.setLogin("admin1");
        user1.setPassword("qwert1234");
        user1.setAge(23);
        boolean actual = storageDao.add(user1) == user1;
        assertTrue(actual, "Your login has been successfully registered!");
    }
}
