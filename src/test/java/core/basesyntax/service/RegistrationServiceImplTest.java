package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setId(123456L);
        user.setLogin("jack");
        user.setPassword("blackman");
        user.setAge(20);
        Storage.people.add(user);
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_addNewUserWithProperCredentials_Ok() {
        user.setId(863210L);
        user.setLogin("kevin");
        user.setPassword("whitecare");
        user.setAge(34);
        User actual = registrationService.register(user);
        assertEquals(user,actual);
    }

    @Test
    void register_addSameUser_notOk() {
        user.setLogin("jack");
        assertThrows(RuntimeException.class,()
                -> registrationService.register(user));
    }

    @Test
    void register_properAge_Ok() {
        user.setLogin("garry");
        user.setAge(25);
        user.setPassword("osborne");
        User actual = registrationService.register(user);
        assertEquals(user,actual);
    }

    @Test
    void register_smallerAge_notOk() {
        user.setLogin("katty");
        user.setAge(10);
        user.setPassword("bailsman");
        assertThrows(RuntimeException.class,()
                -> registrationService.register(user));
    }

    @Test
    void register_largerAge_notOk() {
        user.setLogin("barry");
        user.setAge(100);
        user.setPassword("townsend");
        assertThrows(RuntimeException.class,()
                -> registrationService.register(user));
    }

    @Test
    void register_properPasswordLength_Ok() {
        user.setLogin("mathew");
        user.setAge(32);
        user.setPassword("greyflow");
        User actual = registrationService.register(user);
        assertEquals(user,actual);
    }

    @Test
    void register_smallerPasswordLength_notOk() {
        user.setLogin("chris");
        user.setPassword("par");
        user.setAge(40);
        assertThrows(RuntimeException.class,() ->
                registrationService.register(user));
    }

    @Test
    void register_largerPasswordLength_notOk() {
        user.setId(53679L);
        user.setLogin("steven");
        user.setPassword("qwertyuiopasd");
        user.setAge(24);
        assertThrows(RuntimeException.class,() ->
                registrationService.register(user));
    }

    @Test
    void register_loginStartWithLetter_notOk() {
        user.setLogin("^james");
        user.setPassword("clarified");
        user.setAge(23);
        assertThrows(RuntimeException.class,() ->
                registrationService.register(user));
    }

    @Test
    void register_nullValue_not0k() {
        assertThrows(RuntimeException.class,()
                -> registrationService.register(null));
    }
}
