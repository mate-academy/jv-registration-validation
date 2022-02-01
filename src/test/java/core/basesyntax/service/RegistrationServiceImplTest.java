package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User userWithBadAge = new User();
    private User userWithBadPassword = new User();
    private User goodUser = new User();
    private User sameUser = new User();

    @BeforeEach
    public void setUp() {
        goodUser.setAge(18);
        goodUser.setLogin("login1245");
        goodUser.setPassword("123456");
        sameUser.setAge(22);
        sameUser.setLogin("login4626");
        sameUser.setPassword("12463578");
        userWithBadAge.setAge(17);
        userWithBadAge.setLogin("login12453");
        userWithBadAge.setPassword("123456");
        userWithBadPassword.setAge(22);
        userWithBadPassword.setLogin("login12356");
        userWithBadPassword.setPassword("123");
    }

    @Test
    public void registerGoodUserOK() {
        User excepted = goodUser;
        User actual = registrationService.register(excepted);
        assertEquals(excepted, actual, "Sorry user is not equals");
    }

    @Test
    public void registerUserWithBadAge() {
        User excepted = userWithBadAge;
        assertThrows(RuntimeException.class,
                () -> {
                    registrationService.register(excepted);
                });
    }

    @Test
    public void registerUserWithBadPassword() {
        User actual = userWithBadPassword;
        assertThrows(RuntimeException.class,
                () -> {
                    registrationService.register(actual);
                });
    }

    @Test
    public void userLoginAlreadyExit() {
        Storage.people.clear();
        User one = sameUser;
        User two = sameUser;
        registrationService.register(one);
        assertThrows(RuntimeException.class,
                () -> {
                    registrationService.register(two);
                });
        int excepted = 1;
        int actual = Storage.people.size();
        assertEquals(excepted, actual, "Sorry storage size after adding is bad" + actual);
    }
}
