package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;
    private User user;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User("testuser", "password123", 20);
        storageDao.add(user);
    }

    @Test
    public void validate_ValidData_Ok() {
        User userOK = new User("validuser", "validpassword", 18);
        assertDoesNotThrow(() -> registrationService.register(userOK));
    }

    @Test
    public void validate_AgeLimit_NotOk() {
        user.setAge(0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(-100);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void validate_LoginLengthTooShort_NotOk() {
        user.setLogin("abs");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setLogin("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void validate_PasswordLengthTooShort_NotOk() {
        user.setPassword("abs");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void validate_UserWithSuchLoginAlreadyExists_NotOk() {
        User newUser = new User("testuser","newpassword", 19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    public void register_NullUser_NotOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }
}
