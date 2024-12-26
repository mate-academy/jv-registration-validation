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
        user = new User();
    }

    @Test
    void loginOfUserAlreadyExists_NotOk() {
        user.setId(12l);
        user.setLogin("sanya67");
        user.setAge(18);
        user.setPassword("123456");
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
        user.setId(11l);
        user.setPassword("890765");
        user.setAge(20);
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
            registrationService.register(user);
                });
    }

    @Test
    void loginNotExist_Ok() {
        user.setId(8l);
        user.setLogin("Deadpool");
        user.setAge(89);
        user.setPassword("90809809");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void loginHaveSizeLessThanAllowable_NotOk() {
        user.setId(2l);
        user.setLogin("sanya");
        user.setPassword("7907001");
        user.setAge(21);
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
            registrationService.register(user);
                });
    }

    @Test
    void loginHaveSizeEqualsAllowableSize_Ok() {
        user.setId(4l);
        user.setLogin("sanya2");
        user.setPassword("7907001767");
        user.setAge(23);
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void loginIsBiggerThanAllowableSize_Ok() {
        user.setId(8l);
        user.setLogin("sanya1256");
        user.setPassword("7907001767");
        user.setAge(23);
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void ageOfUserLessThanAllowableAge() {
        user.setId(78l);
        user.setAge(15);
        user.setLogin("VenoM!!!");
        user.setPassword("yufgugfyufguy");
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()-> {
            registrationService.register(user);
                });
    }

    @Test
    void ageOfUserEqualsAllowableAge_Ok() {
        user.setId(12l);
        user.setAge(18);
        user.setLogin("JaxTaller!");
        user.setPassword("yufgugfyufguy1123");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void ageOfUserBiggerThanAllowableAge_Ok() {
        user.setId(17l);
        user.setAge(45);
        user.setLogin("JaxTaller!isAlive");
        user.setPassword("yufgu121123");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void passwordLengthOfUserLessThanAllowableLength_NotOk() {
        user.setId(77l);
        user.setAge(24);
        user.setPassword("gyfyt");
        Assertions.assertThrows(ImpossibleRegisterUserException.class,
                ()->{
            registrationService.register(user);
                });
    }

    @Test
    void passwordLengthOfUserEqualsAllowableLength_Ok() {
        user.setId(123l);
        user.setAge(49);
        user.setLogin("DyingLight");
        user.setPassword("121123");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

    @Test
    void passwordLengthOfUserBiggerThanAllowableLength_Ok() {
        user.setId(177l);
        user.setAge(36);
        user.setLogin("DyingLightTheFollowing");
        user.setPassword("121123gghugugghg");
        try {
            Assert.assertEquals(user, registrationService.register(user));
        } catch (ImpossibleRegisterUserException e) {
            System.out.println("Can`t register this user");
        }
    }

}
