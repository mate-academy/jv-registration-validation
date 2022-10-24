package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("Tretiak");
        user.setPassword("123456");
        user.setAge(20);
    }


    @Test
    void register_noExistUserLogin_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_containsUserLogin_NotOk() {
        Storage.people.add(user);
        try{
            registrationService.register(user);
        } catch (RuntimeException e){
            return;
        }
        fail("User already exist!");
    }

    @Test
    void register_nullUserLogin_NotOk() {
        user.setLogin(null);
        try{
            registrationService.register(user);
        } catch (RuntimeException e){
            return;
        }
        fail("Login can't be null!");
    }

    @Test
    void register_nullUser_NotOk() {
        user = null;
        try{
            registrationService.register(user);
        } catch (RuntimeException e){
            return;
        }
        fail("User can't be null!");
    }

    @Test
    void register_smallPassword_NotOk() {
        user.setPassword("123");
        try{
            registrationService.register(user);
        } catch (RuntimeException e){
            return;
        }
        fail("Password less than 6 symbols!");
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        try{
            registrationService.register(user);
        } catch (RuntimeException e){
            return;
        }
        fail("Password can't be null!");
    }

    @Test
    void register_lowAge_NotOk() {
        user.setAge(15);
        try{
            registrationService.register(user);
        } catch (RuntimeException e){
            return;
        }
        fail("Age less than 18 years old!");
    }
}