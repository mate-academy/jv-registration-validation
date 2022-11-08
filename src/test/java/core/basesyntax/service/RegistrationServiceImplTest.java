package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATIONSERVICE = new RegistrationServiceImpl();
    private static final StorageDao STORAGEDAO = new StorageDaoImpl();

    @Test
    void register_userInStorage_NotOk() {
        User user1 = new User();
        user1.setId(7575L);
        user1.setLogin("qwe");
        user1.setPassword("123456");
        user1.setAge(36);
        STORAGEDAO.add(user1);

        User user2 = new User();
        user2.setId(789L);
        user2.setLogin("qwe");
        user2.setPassword("457627");
        user2.setAge(42);
        assertThrows(RuntimeException.class, () -> REGISTRATIONSERVICE.register(user2));
    }

    @Test
    void register_validData_Ok() {
        User user = new User();
        user.setId(123L);
        user.setLogin("sdhgs5s");
        user.setPassword("qwerds");

        user.setAge(28);
        assertEquals(user, REGISTRATIONSERVICE.register(user));
    }

    @Test
    void register_passwordNull_NotOk() {
        User user = new User();
        user.setId(123L);
        user.setLogin("sdhgss");
        user.setPassword(null);
        user.setAge(45);
        assertThrows(NullPointerException.class, () -> REGISTRATIONSERVICE.register(user));
    }

    @Test
    void register_loginNull_NotOk() {
        User user = new User();
        user.setId(123L);
        user.setPassword("ghghgh");
        user.setAge(25);
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> REGISTRATIONSERVICE.register(user));
    }

    @Test
    public void register_ageLessThanAdult_NotOk() {
        User user = new User();
        user.setId(789L);
        user.setLogin("fhg");
        user.setPassword("789456123");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> REGISTRATIONSERVICE.register(user));
    }

    @Test
    void register_lengthPasswordLessThanSix_NotOk() {
        User user = new User();
        user.setId(15L);
        user.setLogin("sdgfsdg");
        user.setPassword("qeeq");
        user.setAge(85);
        assertThrows(RuntimeException.class, () -> REGISTRATIONSERVICE.register(user));
    }
}

