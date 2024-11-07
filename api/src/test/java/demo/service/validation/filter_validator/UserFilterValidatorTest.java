package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.controller.dto.UserFilterDto;
import demo.service.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static demo.service.validation.filter_validator.FilterErrors.MAX_FILTER_LENGTH_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.MIN_FILTER_LENGTH_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserFilterValidatorTest {

    private UserFilterDtoValidator userFilterDtoValidator;

    @BeforeEach
    void setUp() {
        userFilterDtoValidator = new UserFilterDtoValidator();
    }

    @ParameterizedTest
    @MethodSource("validStringValues")
    void validateString_shouldReturnTrue(UserFilterDto userFilterDto, FilterErrors error) {
        ValidationResult result = userFilterDtoValidator.validate(userFilterDto);
        assertFalse(result.isValid());
        assertEquals(result.getMessage(), error);
    }

    private static Stream<Arguments> validStringValues() {
        return Stream.of(
                Arguments.of(new UserFilterDto().setFilters(List.of()).setName("first"), MIN_FILTER_LENGTH_ERROR ),
                Arguments.of(new UserFilterDto().setName("second").setFilters(getMaxElements()), MAX_FILTER_LENGTH_ERROR )
        );
    }

    private static List<FilterDto> getMaxElements() {
        return Collections.nCopies(6, new FilterDto());
    }
}