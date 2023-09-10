package demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SectorDto {
    private String name;
    private List<SectorDto> children = new ArrayList<>();
}
