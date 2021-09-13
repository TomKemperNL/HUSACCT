package husacct.analyse.domain.layering;

import java.util.Collection;
import java.util.Objects;

public class Layer {
    private String name;
    private Collection<SoftwareUnit> contents;

    public Layer(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layer aClass = (Layer) o;
        return name.equals(aClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Iterable<SoftwareUnit> getContents() {
        return contents;
    }
}
