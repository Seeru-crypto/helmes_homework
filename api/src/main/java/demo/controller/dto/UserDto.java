package demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDto {
    private String name;
    private boolean agreeToTerms;
    private List<SectorDto> sectors;
}
