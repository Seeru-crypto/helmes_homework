package demo.controller.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserDto {
    // Had to add ID, since on the UI, I could not extract it from the location header.
    // In live prod, this would be a security issue. Since every ID would be exposed.

    // UUID, would be a better choice, but then I would have to change the DB schema.
    private UUID id;

    @NotNull
    private String name;

    private String email;

    private String phoneNumber;

    private Instant dob;

    private int height;

    @AssertTrue
    private boolean agreeToTerms;

    @Size(min=1)
    private List<String> sectorNames;
}
