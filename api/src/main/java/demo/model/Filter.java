package demo.model;


import demo.service.filter.DataTypes;
import demo.service.filter.UserFieldNames;
import jakarta.persistence.*;
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

  private String criteria; // CONTAINS, DOES_NOT_CONTAIN

  private DataTypes type; //   STRING, DATE, NUMBER

  private String value; // searchCrietria a, 1, or date

  @NotNull
  private UserFieldNames fieldName;

  private Long userFilterId;
}