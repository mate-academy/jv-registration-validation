package core.basesyntax.middleware.user.stringparams;

import core.basesyntax.middleware.Middleware;

public abstract class StringParamsValidationMiddleware extends Middleware {
    protected final int minLength;

    protected StringParamsValidationMiddleware(int minLength) {
        this.minLength = minLength;
    }
}
