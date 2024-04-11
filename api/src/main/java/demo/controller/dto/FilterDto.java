package demo.controller.dto;

import demo.service.filter.DataTypes;
import demo.service.filter.FieldNames;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilterDto {
    @NotNull
    private DataTypes type;

    @NotNull
    private String criteria;

    @NotNull
    private String value;

    @NotNull
    private FieldNames fieldName; // missing the actual fieldName "name, dob....."

}
