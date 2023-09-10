package demo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDto {
    // Had to add ID, since on the UI, I could not extract it from the location header.
    // In live prod, this would be a security issue. Since every ID would be exposed.
    private Long id;
    private String name;
    private boolean agreeToTerms;
    private List<SectorDto> sectors;
}
