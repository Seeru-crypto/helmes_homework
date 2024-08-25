package demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
public class Sector {

    private Long id;

    private String name;

    private int value;

    private Long parentId;

    private List<Sector> children = new ArrayList<>();

    public void addChild(Sector child) {
        children.add(child);
    }

    public void removeChild(Sector child) {
        children.remove(child);
    }

    public void removeAllChildren() {
        children.clear();
    }

    public Sector setChildren(List<Sector> sectors) {
        this.children.clear();
        this.children.addAll(sectors);
        return this;
    }
}
