package implementations;

import interfaces.DefaultConstructorIntf;
import interfaces.DoubleInjectConstructorIntf;
import interfaces.SingleInjectConstructorIntf;
import org.example.annotations.Inject;

public class DoubleInjectConstructorImpl implements DoubleInjectConstructorIntf {
    private SingleInjectConstructorIntf singleInjectConstructorIntf;
    private DefaultConstructorIntf defaultConstructorIntf;

    @Inject
    public DoubleInjectConstructorImpl(
            SingleInjectConstructorIntf singleInjectConstructorIntf, DefaultConstructorIntf defaultConstructorIntf) {
        this.singleInjectConstructorIntf = singleInjectConstructorIntf;
        this.defaultConstructorIntf = defaultConstructorIntf;
    }

    public SingleInjectConstructorIntf getSingleInjectConstructorIntf() {
        return singleInjectConstructorIntf;
    }

    public void setSingleInjectConstructorIntf(SingleInjectConstructorIntf singleInjectConstructorIntf) {
        this.singleInjectConstructorIntf = singleInjectConstructorIntf;
    }

    public DefaultConstructorIntf getDefaultConstructorIntf() {
        return defaultConstructorIntf;
    }

    public void setDefaultConstructorIntf(DefaultConstructorIntf defaultConstructorIntf) {
        this.defaultConstructorIntf = defaultConstructorIntf;
    }
}
