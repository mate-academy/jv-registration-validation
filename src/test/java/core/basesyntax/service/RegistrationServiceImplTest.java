package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MIN_USER_AGE = 18;
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;

    @Test
    void register_sameLogin_NotOk() {
        User testUser = new User();
        testUser.setAge(20);
        testUser.setLogin("Mersedes");
        testUser.setPassword("king_1975");
        storageDao.add(testUser);
        User user = new User();
        user.setLogin(testUser.getLogin());
        user.setPassword("somepassword");
        user.setAge(25);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user = new User();
        user.setAge(19);
        user.setLogin("kijuljhbhzt");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        User user = new User();
        user.setPassword("somevalidpassword");
        user.setAge(19);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user = new User();
        user.setLogin("kijäloonjiuhzt");
        user.setPassword("somevalid");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User();
        user.setAge(-1);
        user.setLogin("kijupoohzt");
        user.setPassword("newidiaa");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_NotOk() {
        User user = new User();
        user.setAge(0);
        user.setLogin("kijüpijuhzt");
        user.setPassword("jdhszdgtr");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThatMinAge_NotOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("kijuhzt");
        user.setPassword("jdhszdgtr");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAge_Ok() {
        User user = new User();
        user.setAge(MIN_USER_AGE);
        user.setLogin("kjgvfujzfuzfpp");
        user.setPassword("ljhzvgljz");
        assertNotNull(registrationService.register(user));
    }

    @Test
    void register_ageMoreThenMinAge_Ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("kijtfuhzt");
        user.setPassword("jdhszdgtrppp");
        assertDoesNotThrow(() -> registrationService.register(user).getAge());
    }

    @Test
    void register_loginEmpty_NotOk() {
        user = new User();
        user.setAge(19);
        user.setLogin("");
        user.setPassword("jdhszpppdgtr");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordMoreThenMinLength_Ok() {
        User user = new User();
        user.setAge(19);
        user.setLogin("llllkkbjklvg");
        user.setPassword("jdhszdlllgtr");
        assertDoesNotThrow(() -> (registrationService.register(user)));
    }

    @Test
    void register_passwordEqualMinLength_Ok() {
        final int Min_Password_length = 6;
        User user = new User();
        user.setAge(MIN_USER_AGE);
        user.setLogin("Victory2022");
        user.setPassword("jkjhgf");
        assertTrue(registrationService.register(user).getPassword().length()
                == Min_Password_length);
    }

    @Test
    void register_passwordLessThanMin_NotOk() {
        User user = new User();
        user.setAge(MIN_USER_AGE);
        user.setLogin("Victory2022");
        user.setPassword("jkj");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_Ok() {
        User user = new User();
        user.setAge(MIN_USER_AGE);
        user.setLogin("Victory2022");
        user.setPassword("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }
}
