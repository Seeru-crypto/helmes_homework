package model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@ToString
@Table
public class Sector extends AbstractAuditingEntity<Long> {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @Column
    private Long parentId;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    private List<Sector> children = new ArrayList<>();
}
