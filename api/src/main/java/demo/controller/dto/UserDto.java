package demo.controller.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    private String name;

    @AssertTrue
    private boolean agreeToTerms;

    @Size(min=1)
    private List<SectorDto> sectors;
}
