package demo.controller.dto;

import jakarta.validation.constraints.NotNull;
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
    private String name;

    @NotNull
    private List<FilterDto> filters;
}
