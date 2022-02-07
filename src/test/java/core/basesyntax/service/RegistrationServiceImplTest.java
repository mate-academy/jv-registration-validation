package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User goodUser = new User();

    @BeforeEach
    public void setUp() {
        goodUser.setAge(18);
        goodUser.setLogin("login1245");
        goodUser.setPassword("123456");
    }

    @Test
    public void register_goodUser_Ok() {
        User excepted = goodUser;
        User actual = registrationService.register(excepted);
        assertEquals(excepted, actual, "Sorry user is not equals");
    }

    @Test
    public void register_userWithBadAge_notOk() {
        User excepted = goodUser;
        excepted.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(excepted));
    }

    @Test
    public void register_userWithBadPassword_notOk() {
        User actual = goodUser;
        actual.setPassword("12345");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void register_userLoginAlreadyExit_notOk() {
        Storage.people.clear();
        User one = goodUser;
        User two = goodUser;
        registrationService.register(one);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(two));
        int excepted = 1;
        int actual = Storage.people.size();
        assertEquals(excepted, actual, "Sorry storage size after adding is bad" + actual);
    }
}
