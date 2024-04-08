package demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table
@ToString
public class UserFilter extends AbstractAuditingEntity<Long> {

  @Id
  @Column(unique = true)
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Column(nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.ALL,
          fetch = FetchType.LAZY,
          orphanRemoval = true)
  @JoinTable(
          name = "filter",
          joinColumns = @JoinColumn(name = "user_filter_id")
  )
  private List<Filter> filters = new ArrayList<>();

  @ManyToOne(fetch = LAZY,
          cascade = MERGE)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
