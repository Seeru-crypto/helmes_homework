package demo.controller.dto;

import demo.model.AbstractAuditingEntity;
import demo.service.filter.DateCriteria;
import demo.service.filter.FieldType;
import demo.service.filter.NumberCriteria;
import demo.service.filter.StringCriteria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table
@ToString
public class FilterOptions extends AbstractAuditingEntity<Long> {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String field; //name, date of birth, age

    @NotNull
    @ElementCollection()
    private List<String> criteriaValues; //CONTAINS, DOES_NOT_CONTAIN

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private FieldType fieldType; // STRING, DATE, NUMBER

    public List<String> getCriteriaValues() {
        return switch (this.fieldType) {
            case STRING -> StringCriteria.getStringCriterias();
            case DATE -> DateCriteria.getStringCriterias();
            case NUMBER -> NumberCriteria.getStringCriterias();
        };
    }
}
