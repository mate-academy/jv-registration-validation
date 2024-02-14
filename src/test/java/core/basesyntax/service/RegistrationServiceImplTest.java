package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User correctUser = new User("CorrectUserLogin","CorrectUserPassword",18);
    private static final User sameLogin1User = new User("sameLogin","sameLogin1UserPassword",19);
    private static final User sameLogin2User = new User("sameLogin","sameLogin2UserPassword",20);
    private static final User shortLoginUser = new User("ShLgn","ShortLoginUserPassword",21);
    private static final User shortPasswordUser = new User("ShortPasswordUserLogin","Pswrd",22);
    private static final User tooYoungUser = new User("TooYoungUserLogin","TooYoungUserPass",17);
    private static final User nullUser = new User(null,null,null);
    private static final User nullLoginUser = new User(null,"nullLoginUserPassword",24);
    private static final User nullPasswordUser = new User("nullPasswordUserLogin",null,25);
    private static final User nullAgeUser = new User("nullAgeUserLogin","nullAgeUserPassword",null);
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_allCorrectData_ok() {
        User actual = registrationService.register(correctUser);
        User expected = storageDao.get(correctUser.getLogin());
        assertEquals(actual,expected);
    }

    @Test
    void register_loginLength_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(shortLoginUser);
        });
    }

    @Test
    void register_passwordLength_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(shortPasswordUser);
        });
    }

    @Test
    void register_underage_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(tooYoungUser);
        });
    }

    @Test
    void register_nullUserInput_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullLoginUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(nullAgeUser);
        });
    }

    @Test
    void register_nullInput_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_addUserIsInStorage_notOk() {
        registrationService.register(sameLogin1User);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameLogin2User);
        });
    }
}
