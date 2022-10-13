package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user = new User();
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @Before
    public void setUser() {
        user.setAge(19);
        user.setLogin("Genareb");
        user.setPassword("asdfgh");
    }

    @Test
    void register_nullUser_notOk() throws RuntimeException {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in user == null case");
    }

    @Test
    void register_nullAge_notOk() {
        setUser();
        user.setAge(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in age == null case");
    }

    @Test
    void register_ageLessMinAge_notOk() {
        setUser();
        user.setAge(17);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in user age less MIN_USERS_AGE case");
    }

    @Test
    void register_nullLogin_notOk() {
        setUser();
        user.setLogin(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in user login == null case");
    }

    @Test
    void register_passwordLessMinChars_notOk() {
        setUser();
        user.setPassword("nullo");
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in user password less MIN_PASSWORDS_LENGTH case");
    }

    @Test
    void register_nullPassword_notOk() {
        setUser();
        user.setPassword(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in user password == null case");
    }

    @Test
    void register_existedLogin_notOk() {
        User user2 = new User();
        user2.setAge(18);
        user2.setPassword("yeoyreoyuoe");
        user2.setLogin("Genareb");
        try {
            registrationService.register(user);
            registrationService.register(user2);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown in case when users login already busy");
    }

    @Test
    void register_idealUser_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        setUser();
        registrationService.register(user);
        User gottenUser = storageDao.get(user.getLogin());
        Assertions.assertEquals(gottenUser,user, "Gotten user should be equal added user");
    }
}
