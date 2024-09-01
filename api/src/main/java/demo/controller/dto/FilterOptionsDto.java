package demo.controller.dto;

import demo.service.filter.DataTypes;
import demo.service.filter.Filters;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FilterOptionsDto {
    @NotNull
    private String field;

    @NotNull
    private List<Filters> criteria;

    @NotNull
    private DataTypes allowedValue;
}
