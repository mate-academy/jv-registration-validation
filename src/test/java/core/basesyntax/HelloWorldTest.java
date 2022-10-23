package core.basesyntax;


import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HelloWorldTest {
   private static RegistrationServiceImpl registrationService;
    User rightUser;
    User usWithWrongPass;
    User usWithWrongAge;

    @BeforeAll
           static void beforeAll(){
         registrationService=new RegistrationServiceImpl();
    }
    @BeforeEach
void setUp(){
rightUser=new User();
rightUser.setId(2L);
rightUser.setAge(22);
rightUser.setPassword("Mypassss12");
rightUser.setLogin("YuraBorly");
        usWithWrongPass=new User();
        usWithWrongPass.setId(2L);
        usWithWrongPass.setAge(25);
        usWithWrongPass.setPassword("Kata");
        usWithWrongPass.setLogin("Katherine12");
        usWithWrongAge=new User();
        usWithWrongAge.setId(2L);
        usWithWrongAge.setAge(15);
        usWithWrongAge.setPassword("MaryMeds1");
        usWithWrongAge.setLogin("MariaMedsia");
    }
    @Test
    void rightUser(){
        User user= registrationService.register(rightUser);
        assertEquals(user,rightUser);
    }
    @Test
    void wrongPassword(){
        assertThrows(RuntimeException.class,()->{
            registrationService.register(usWithWrongPass);
        });
    }
    @Test
    void wrongAge(){
        assertThrows(RuntimeException.class,()->{
            registrationService.register(usWithWrongAge);
        });
    }
    @Test
    void userAlreadyExist(){
        assertThrows(RuntimeException.class,()->{
            registrationService.register(rightUser);
        });
    }

    @Test
void register_nullAge_notOk(){
        assertThrows(NullPointerException.class,()->{
            registrationService.register(null);
        });
    }
}

