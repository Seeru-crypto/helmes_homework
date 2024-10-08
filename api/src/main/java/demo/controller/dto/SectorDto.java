package demo.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SectorDto {

    @NotNull
    private String name;

    private Long id;

    private Long parentId;

    @NotNull
    private int value;

    private List<SectorDto> children = new ArrayList<>();
}
