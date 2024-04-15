package demo.controller.dto;

import demo.service.filter.DataTypes;
import demo.service.filter.UserFieldNames;
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
    private UserFieldNames fieldName; // missing the actual fieldName "name, dob....."

}
