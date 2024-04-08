package demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table
@ToString
public class Filter extends AbstractAuditingEntity<Long> {

  @Id
  @Column(unique = true)
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String criteria;

  private String type;

  private String value;

  private Long userFilterId;
}
