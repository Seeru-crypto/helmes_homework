package demo.model;


import demo.service.filter.DataTypes;
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

  private DataTypes type;

  private String value;

  private Long userFilterId;
}
