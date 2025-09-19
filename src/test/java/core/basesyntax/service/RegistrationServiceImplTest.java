package core.basesyntax.service;

import core.basesyntax.exeptions.*;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private List<User> usersList;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        people.clear();
        this.registrationService = new RegistrationServiceImpl();
        usersList = new ArrayList<>();
        User max = new User(1L, "max123", "1234567",20);
        User alex = new User(2L, "alex@gmail.com", "1234567",17);
        User bella = new User(3L, "bella", "1234567",18);
        User carloss = new User(4L, "carloss@gmail.com", "1234567",24);
        User kelly = new User(5L, "kelly@gmail.com", "1234",24);
        User lando = new User(6L, "lando@gmail.com", "123456",18);
        User elizabet = new User(7L, "elizabet@gmail.com", "1234567",68);
        User oskar = new User(8L, "oskar@gmail.com", "1234567",25);
        User charles = new User(9L, "charles@gmail.com", "1234567",18);
        User lewis = new User(10L, "lewis", "123",15);
        User george = new User(11L, null, "1234567",20);
        User kimmy = new User(12L, "kimmy@gmail.com", null,20);
        User alexandra = new User(13L, "alexandra@gmail.com", "1234567",-20);
        User mario = new User(14L, "mario@gmail.com", "1234567",null);
        User alonso = new User(15L, "alonso@gmail.com", "",22);
        User fernando = new User(16L, "fernando@gmail.com", "12345",22);
        User antonelli = new User(17L, "", "1234567",22);
        usersList.add(max); //0
        usersList.add(alex); //1
        usersList.add(bella); //2
        usersList.add(carloss); //3
        usersList.add(kelly); //4
        usersList.add(lando); //5
        usersList.add(elizabet); //6
        usersList.add(oskar); //7
        usersList.add(charles); //8
        usersList.add(lewis); // 9
        usersList.add(george); //10
        usersList.add(kimmy); //11
        usersList.add(alexandra); //12
        usersList.add(mario); //13
        usersList.add(alonso); // 14
        usersList.add(fernando); //15
        usersList.add(antonelli);//16
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        assertThrows(PasswordShorterThanSixException.class,
                () -> registrationService.register(usersList.get(14)));
    }

    @Test
    void register_passwordIsFive_notOk() {
        assertThrows(PasswordShorterThanSixException.class,
                () -> registrationService.register(usersList.get(15)));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        assertThrows(LoginShorterThanSixException.class,
                () -> registrationService.register(usersList.get(16)));
    }

    @Test
    void register_allCriteriesAreFailed_notOk() {
        assertThrows(LoginShorterThanSixException.class,
                () -> registrationService.register(usersList.get(9)));
    }

    @Test
    void register_userEqualsNull_notOk() {
        assertThrows(UserIsNullException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        people.add(usersList.get(0));
        assertThrows(UserAlreadyExistsException.class,
                () -> registrationService.register(usersList.get(0)));
    }

    @Test
    void register_loginShorterThanSix_notOk() {
        assertThrows(LoginShorterThanSixException.class,
                () -> registrationService.register(usersList.get(2)));
    }

    @Test
    void register_loginIsSix_ok() {
        User testUser = usersList.get(0);
        User returned = registrationService.register(testUser);
        assertTrue(people.contains(returned));
        assertEquals(people.get(0), testUser);
    }

    @Test
    void register_loginLongerThanSix_ok() {
        User testUser = usersList.get(3);
        User returned = registrationService.register(testUser);
        assertTrue(people.contains(returned));
        assertEquals(people.get(0), testUser);
    }

    @Test
    void register_passwordShorterThanSix_notOk() {
        assertThrows(PasswordShorterThanSixException.class,
                () -> registrationService.register(usersList.get(4)));
    }

    @Test
    void register_passwordIsSix_ok() {
        User testUser = usersList.get(5);
        User returned = registrationService.register(testUser);
        assertTrue(people.contains(returned));
        assertEquals(people.get(0), testUser);
    }

    @Test
    void register_passwordLongerThanSix_ok() {
        User testUser = usersList.get(6);
        User returned = registrationService.register(testUser);
        assertTrue(people.contains(returned));
        assertEquals(people.get(0), testUser);
    }

    @Test
    void register_userYoungerThanEighteen_notOk() {
        assertThrows(UserYoungerThanEighteenException.class,
                () -> registrationService.register(usersList.get(1)));
    }

    @Test
    void register_userIsEighteen_ok() {
        User testUser = usersList.get(8);
        User returned = registrationService.register(testUser);
        assertTrue(people.contains(returned));
        assertEquals(people.get(0), testUser);
    }

    @Test
    void register_userOlderThanEighteen_ok() {
        User testUser = usersList.get(7);
        User returned = registrationService.register(testUser);
        assertTrue(people.contains(returned));
        assertEquals(people.get(0), testUser);
    }

    @Test
    void register_loginIsNull_notOk() {
        assertThrows(LoginIsNullException.class,
                () -> registrationService.register(usersList.get(10)));
    }

    @Test
    void register_passwordIsNull_notOk() {
        assertThrows(PasswordIsNullException.class,
                () -> registrationService.register(usersList.get(11)));
    }

    @Test
    void register_ageIsNull_notOk() {
        assertThrows(AgeIsNullException.class,
                () -> registrationService.register(usersList.get(13)));
    }

    @Test
    void register_ageIsMinus_notOk() {
        assertThrows(UserYoungerThanEighteenException.class,
                () -> registrationService.register(usersList.get(12)));
    }
}