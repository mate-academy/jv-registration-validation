package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImp registrationService;
    private final StorageDaoImpl storageMock;

    RegistrationServiceImplTest(StorageDaoImpl storageMock) {
        this.storageMock = storageMock;
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImp(storageMock);
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = new User(2147L, "existingUser", "password123", 20);
        registrationService.register(user);
    }

    @Test
    public void register_loginTooShort_notOk() {
        User user = new User(2147L, "short", "password123", 20);
        registrationService.register(user);
    }

    @Test
    public void register_passwordTooShort_notOk() {
        User user = new User(2147L, "validLogin", "pass", 20);
        registrationService.register(user);
    }

    @Test
    public void register_ageLessThan18_notOk() {
        User user = new User(2147L, "validLogin", "password123", 17);
        registrationService.register(user);
    }

    @Test
    public void register_validUser_ok() {
        User user = new User(2147L, "validLogin", "password123", 20);
        registrationService.register(user);
    }
}
