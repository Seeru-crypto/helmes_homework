package demo.controller.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveUserDto {
    @NotNull
    @Size(max = 20)
    private String name;

    @Size(max = 20)
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    @Size(min=1, max = 10)
    private List<Long> sectorIds;

    @AssertTrue
    private boolean agreeToTerms;

    private Instant dob;
}
