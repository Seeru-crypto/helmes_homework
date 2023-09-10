package demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SaveUserDto {
    private String name;
    private List<SectorDto> sectors;
    private boolean agreeToTerms;
}
