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
@Entity
@Table(name = "sector")
@ToString
public class SectorEntity extends AbstractAuditingEntity<Long> {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    private int value;

    @Column
    private Long parentId;

    @OneToMany(cascade = MERGE,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "parentId")
    private List<SectorEntity> children = new ArrayList<>();

    public void addChild(SectorEntity child) {
        children.add(child);
    }

    public void removeChild(SectorEntity child) {
        children.remove(child);
    }

    public void removeAllChildren() {
        children.clear();
    }

    public SectorEntity setChildren(List<SectorEntity> sectors) {
        this.children.clear();
        this.children.addAll(sectors);
        return this;
    }
}
