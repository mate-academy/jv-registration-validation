package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setup() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_validUser_Ok() {
        User validUser = new User("validLogin", "validPassword", 18);
        registrationService.register(validUser);
    }

    @Test
    void register_invalidAge_NotOk() {
        User invalidAgeUser = new User("invalidAge", "validPassword", 17);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_shortLogin_NotOk() {
        User shortLoginUser = new User("short", "validPassword", 18);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(shortLoginUser));
    }

    @Test
    void register_shortPassword_NotOk() {
        User shortPasswordUser = new User("validLogin", "short", 18);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(shortPasswordUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        User nullLoginUser = new User(null, "validPassword", 18);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        User nullPasswordUser = new User("validLogin", null, 18);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_nullAge_NotOk() {
        User nullAgeUser = new User("validLogin", "validPassword", 0);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_existingUser_NotOk() {
        User existingUser = new User("existingUser", "validPassword", 18);
        storageDao.add(existingUser);
        org.junit.jupiter.api.Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(existingUser));
    }
}
