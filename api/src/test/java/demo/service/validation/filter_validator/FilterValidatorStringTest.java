package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.FieldType;
import demo.service.filter.DateCriteria;
import demo.service.filter.StringCriteria;
import demo.service.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static demo.service.validation.filter_validator.FilterErrors.INVALID_CRITERIA_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterValidatorStringTest {

    private FilterDtoValidator filterDtoValidator;

    @BeforeEach
    void setUp() {
        filterDtoValidator = new FilterDtoValidator();
    }

    @ParameterizedTest
    @MethodSource("validStringValues")
    void validateString_shouldReturnTrue(String value) {
        FilterDto filterDto = new FilterDto()
                .setType(FieldType.STRING)
                .setCriteriaValue(StringCriteria.CONTAINS.getCode())
                .setValue(value);
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    private static Stream<String> validStringValues() {
        return Stream.of("abc", "2wess", "2" );
    }

    @ParameterizedTest
    @MethodSource("invalidStringCriteriaNumericCriteria")
    void validateString_shouldReturnFalse_whenCriteriaInvalid(String criteria) {
        FilterDto filterDto = new FilterDto()
                .setType(FieldType.STRING)
                .setCriteriaValue(criteria)
                .setValue("abc");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());
    }

    private static Stream<String> invalidStringCriteriaNumericCriteria() {
        return Stream.of(DateCriteria.BEFORE.getCode(), "invalidCriteria");
    }
}