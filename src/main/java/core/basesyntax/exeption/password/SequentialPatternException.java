package core.basesyntax.exeption.password;

import core.basesyntax.exeption.RegistrationServiceException;

public class SequentialPatternException extends RegistrationServiceException {
    public SequentialPatternException(String sequentialPattern) {
        super("Password contains a sequential pattern: " + sequentialPattern);
    }
}
