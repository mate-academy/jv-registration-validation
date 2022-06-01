package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int AGE = 18;
    public static final int PASSWORD = 6;
    private RegistrationServiceImpl regService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;

    @Test
    void register_Login_NotOk() {
        User testUser = new User();
        user = new User();
        testUser.setAge(20);
        testUser.setLogin("Mersedes");
        testUser.setPassword("king_1975");
        storageDao.add(testUser);
        user.setLogin("Mersedes");
        assertThrows(RuntimeException.class, () ->
                regService.register(user).equals(storageDao.get(user.getLogin())));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user = new User();
        user.setAge(19);
        user.setLogin("kijuljhbhzt");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user = new User();
        user.setAge(19);
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user = new User();
        user.setLogin("kijäloonjiuhzt");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user = new User();
        user.setAge(-1);
        user.setLogin("kijupoohzt");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void ageZero_NotOk() {
        user = new User();
        user.setAge(0);
        user.setLogin("kijüpijuhzt");
        user.setPassword("jdhszdgtr");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_ageLessEighteen_NotOk() {
        user = new User();
        user.setAge(17);
        user.setLogin("kijuhzt");
        user.setPassword("jdhszdgtr");
        assertThrows(RuntimeException.class, () -> regService.register(user));
    }

    @Test
    void register_ageEighteen() {
        user = new User();
        user.setAge(18);
        user.setLogin("kjgvfujzfuzfpp");
        user.setPassword("ljhzvgljz");
        assertNotNull(regService.register(user));/////
    }

    @Test
    void register_ageMoreThenEighteen_Ok() {
        user = new User();
        user.setAge(19);
        user.setLogin("kijtfuhzt");
        user.setPassword("jdhszdgtrppp");
        assertDoesNotThrow(() -> regService.register(user).getAge());/////
    }

    @Test
    void loginEmpty_Ok() {
        user = new User();
        user.setAge(19);
        user.setLogin("");
        user.setPassword("jdhszpppdgtr");
        assertThrows(RuntimeException.class, () -> regService.register(user).getLogin());
    }

    @Test
    void register_passwordMoreTheSix_Ok() {
        user = new User();
        user.setAge(19);
        user.setLogin("llllkkbjklvg");
        user.setPassword("jdhszdlllgtr");
        assertDoesNotThrow(() -> (regService.register(user).getPassword()));/////
    }

    @Test
    void register_passwordIsSix_Ok() {
        user = new User();
        user.setAge(18);
        user.setLogin("Victory2022");
        user.setPassword("jkjhgf");
        assertTrue(regService.register(user).getPassword().length() == 6);
    }

    @Test
    void register_passwordLessSix_Ok() {
        user = new User();
        user.setAge(AGE);
        user.setLogin("Victory2022");
        user.setPassword("jkj");
        assertThrows(RuntimeException.class, () -> regService.register(user).getPassword());
    }

    @Test
    void register_passwordIsEmpty_Ok() {
        user = new User();
        user.setAge(AGE);
        user.setLogin("Victory2022");
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> regService.register(user).getPassword());
    }
}
