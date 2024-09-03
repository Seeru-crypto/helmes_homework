package demo.controller.dto;

import demo.service.filter.FieldType;
import demo.service.filter.UserFieldNames;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilterDto {
    @NotNull
    @Enumerated(EnumType.STRING)
    private FieldType type;

    @NotNull
    private String criteriaValue;

    @NotNull
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserFieldNames fieldName; // missing the actual fieldName "name, dob....."

}
