package implementations;

import interfaces.DefaultConstructorIntf;
import interfaces.SingleInjectConstructorIntf;
import org.example.annotations.Inject;

public class SingleInjectConstructorImpl implements SingleInjectConstructorIntf {
    @Inject
    public SingleInjectConstructorImpl(DefaultConstructorIntf defaultConstructorIntf) {
    }
}
