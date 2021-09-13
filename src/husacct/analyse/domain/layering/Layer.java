package husacct.analyse.domain.layering;

import java.util.*;

public class Layer {
    private String name;
    private Collection<SoftwareUnit> contents;

    public Layer(String name) {
        this(name, new ArrayList<>());
    }

    public Layer(String name, List<SoftwareUnit> contents) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;

        if (contents == null) {
            throw new NullPointerException("contents");
        }
        this.contents = contents;
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

    public List<SoftwareUnit> getContents() {
        return new ArrayList<>(contents);
    }
}
