package controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SectorDto {
    private Long id;
    private String name;
    private Long parentId;
    private List<SectorDto> children = new ArrayList<>();
}
