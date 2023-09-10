package demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Size(max = 200)
    private String name;

    private boolean agreeTerms;

    @ManyToMany(fetch = LAZY,
            cascade = MERGE)
    @JoinTable(
            name = "user_sectors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    List<Sector> sectors = new ArrayList<>();
}
