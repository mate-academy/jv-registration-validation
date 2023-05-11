package core.basesyntax.middleware.user.age;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.middleware.Middleware;
import core.basesyntax.model.User;

public class AgeValidationMiddleware extends Middleware {
    private final Integer minAge;

    public AgeValidationMiddleware(Integer minAge) {
        this.minAge = minAge;
    }

    @Override
    public void check(User user) {
        Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (age < minAge) {
            throw new RegistrationException(
                    "You should be at least " + minAge + " years old in order to register; "
                    + "Your age: " + age);
        }
        checkNext(user);
    }
}
