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
public class SaveUserDto {

    @NotNull
    private String name;

    @Size(min=1)
    private List<String> sectors;

    @AssertTrue
    private boolean agreeToTerms;
}
