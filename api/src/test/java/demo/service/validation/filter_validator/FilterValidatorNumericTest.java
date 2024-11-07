package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.FieldType;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
import demo.service.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static demo.service.validation.filter_validator.FilterErrors.INVALID_CRITERIA_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.INVALID_NUMBER_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterValidatorNumericTest {

    private FilterDtoValidator filterDtoValidator;

    @BeforeEach
    void setUp() {
        filterDtoValidator = new FilterDtoValidator();
    }

    @ParameterizedTest
    @MethodSource("validNumericValues")
    void validateNumeric_shouldReturnTrue(String value) {
        FilterDto filterDto = new FilterDto()
                .setType(FieldType.NUMBER)
                .setCriteriaValue(NumberCriteria.SMALLER_THAN.getCode())
                .setValue(value);
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    static Stream<String> validNumericValues() {
        return Stream.of(
                "1", "666", "69"
        );
    }

    @ParameterizedTest
    @MethodSource("invalidNumericCriteria")
    void validateNumeric_shouldReturnFalse_whenCriteriaInvalid(String criteria, FilterErrors error) {
        FilterDto filterDto = new FilterDto()
                .setType(FieldType.NUMBER)
                .setCriteriaValue(criteria)
                .setValue("2");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(error, result.getMessage());
    }

    private static Stream<Arguments> invalidNumericCriteria() {
        return Stream.of(
                Arguments.of(DateCriteria.BEFORE.getCode(), INVALID_CRITERIA_ERROR),
                Arguments.of("invalidCriteria", INVALID_CRITERIA_ERROR)

        );
    }

    @ParameterizedTest
    @MethodSource("invalidNumericValues")
    void validateNumeric_shouldReturnFalse_whenValueInvalid(String values, FilterErrors error) {
        FilterDto filterDto = new FilterDto()
                .setType(FieldType.NUMBER)
                .setCriteriaValue(NumberCriteria.SMALLER_THAN.getCode())
                .setValue(values);
        ValidationResult result = filterDtoValidator.validate(filterDto);

        assertFalse(result.isValid());
        assertEquals(error, result.getMessage());
    }

    private static Stream<Arguments> invalidNumericValues() {
        return Stream.of(
                Arguments.of("a", INVALID_NUMBER_VALUE),
                Arguments.of("1.0", INVALID_NUMBER_VALUE),
                Arguments.of("a2s", INVALID_NUMBER_VALUE),
                Arguments.of(null, INVALID_NUMBER_VALUE)
        );
    }
}