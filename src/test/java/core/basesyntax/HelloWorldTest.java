package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.ImpossibleRegisterUserException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user.setId(12l);
        user.setLogin("sanya67");
        user.setAge(18);
        user.setPassword("123456");
    }

    @Test
    void loginOfUserAlreadyExists_NotOk() {
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("this login isn`t available");
        }
        User user1 = new User();
        user.setId(12l);
        user.setLogin("sanya67");
        user.setAge(18);
        user.setPassword("123456");
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
                    registrationService.register(user1);
                });
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
            registrationService.register(user);
                });
    }

    @Test
    void loginNotExist_Ok() {
        user.setLogin("Deadpool");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void loginHaveSizeLessThanAllowable_NotOk() {
        user.setLogin("sanya");
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
            registrationService.register(user);
                });
    }

    @Test
    void loginHaveSizeEqualsAllowableSize_Ok() {
        user.setLogin("sanya2");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void loginIsBiggerThanAllowableSize_Ok() {
        user.setLogin("sanya1256");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void ageOfUserLessThanAllowableAge() {
        user.setAge(15);
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
            registrationService.register(user);
                });
    }

    @Test
    void ageOfUserEqualsAllowableAge_Ok() {
        user.setAge(18);
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void ageOfUserBiggerThanAllowableAge_Ok() {
        user.setAge(45);
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void passwordLengthOfUserLessThanAllowableLength_NotOk() {
        user.setPassword("gyfyt");
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()->{
            registrationService.register(user);
                });
    }

    @Test
    void passwordLengthOfUserEqualsAllowableLength_Ok() {
        user.setPassword("121123");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void passwordLengthOfUserBiggerThanAllowableLength_Ok() {
        user.setPassword("121123gghugugghg");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

}
