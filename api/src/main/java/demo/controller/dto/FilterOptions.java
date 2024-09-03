package demo.controller.dto;

import demo.model.AbstractAuditingEntity;
import demo.service.filter.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static demo.service.filter.FieldType.DATE;
import static demo.service.filter.FieldType.STRING;
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
        if (this.fieldType == STRING) {
            return StringCriteria.getStringCriterias();
        }

        else if (this.fieldType == DATE) {
            return DateCriteria.getStringCriterias();
        }

        else {
            return NumberCriteria.getStringCriterias();
        }
    }
}
