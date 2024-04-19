package demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserFilterDto {

    private Long id;

    @NotNull
    @Size(min = 4, message = "MIN_FILTER_LENGTH_ERROR")
    @Size(max = 50, message = "MAX_FILTER_LENGTH_ERROR")
    private String name;

    @NotNull
    @Size(min=1, max=5)
    private List<FilterDto> filters;
}
