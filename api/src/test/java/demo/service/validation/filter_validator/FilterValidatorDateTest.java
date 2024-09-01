package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.DataTypes;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
import demo.service.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.stream.Stream;

import static demo.service.validation.filter_validator.FilterErrors.INVALID_CRITERIA_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.INVALID_DATE_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterValidatorDateTest {

    private FilterDtoValidator filterDtoValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filterDtoValidator = new FilterDtoValidator();
    }

    @Test
    void validateDateValues_shouldReturnTrue() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.DATE)
                .setCriteria(DateCriteria.BEFORE.getCode())
                .setValue(Instant.now().toString());
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @ParameterizedTest
    @MethodSource("invalidDateCriteria")
    void validateDateValues_shouldReturnFalse(String criteria, FilterErrors error) {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.DATE)
                .setCriteria(criteria)
                .setValue(Instant.now().toString());
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(error, result.getMessage());
    }

    private static Stream<Arguments> invalidDateCriteria() {
        return Stream.of(
                Arguments.of(NumberCriteria.SMALLER_THAN.getCode(), INVALID_CRITERIA_ERROR),
                Arguments.of("invalidCriteria", INVALID_CRITERIA_ERROR),
                Arguments.of(null, INVALID_CRITERIA_ERROR)

        );
    }

    @ParameterizedTest
    @MethodSource("invalidDateValues")
    void validateDateValues_shouldReturnFalse_whenValueIsNotValid(String value, FilterErrors error) {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.DATE)
                .setCriteria(DateCriteria.BEFORE.getCode())
                .setValue(value);
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(error, result.getMessage());
    }

    private static Stream<Arguments> invalidDateValues() {
        return Stream.of(
                Arguments.of("abc", INVALID_DATE_VALUE),
                Arguments.of("123", INVALID_DATE_VALUE),
                Arguments.of(null, INVALID_DATE_VALUE)

        );
    }
}