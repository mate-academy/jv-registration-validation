package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void validate_AgeLessThan18_NotOk() {
        final User user = new User("bestlogin", "secretpassword", 15);
        final User user1 = new User("bestlogin", "secretpassword", -1);
        final User user2 = new User("bestlogin", "secretpassword", 0);
        final User user3 = new User("bestlogin", "secretpassword", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user2));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user3));
    }

    @Test
    public void validate_LoginLessThan6_NotOk() {
        final User user = new User("", "secretpassword", 15);
        final User user1 = new User("abc", "secretpassword", -1);
        final User user2 = new User("1234", "secretpassword", 0);
        final User user3 = new User("login", "secretpassword", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user2));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user3));
    }

    @Test
    public void validate_PasswordLessThan6_NotOk() {
        final User user = new User("mylogin", " ", 15);
        final User user1 = new User("mylogin", "lolke", -1);
        final User user2 = new User("mylogin", "", 0);
        final User user3 = new User("mylogin", "abc", 17);
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user2));
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user3));
    }

    @Test
    public void validate_UserWithSuchLoginAlreadyExists_NotOk() {
        storageDao = new StorageDaoImpl();
        storageDao.add(new User("coollogin", "secretpassword", 25));
        final User user = new User("coollogin", "banana123", 20);
        assertThrows(InvalidDataException.class, () -> registrationService.validate(user));
    }
}
