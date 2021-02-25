package implementations;

import interfaces.ManyInjectConstructorsIntf;
import org.example.annotations.Inject;

public class ManyInjectConstructorsImpl implements ManyInjectConstructorsIntf {
    @Inject
    public ManyInjectConstructorsImpl(Integer obj) {}

    @Inject
    public ManyInjectConstructorsImpl(String str) {}
}
