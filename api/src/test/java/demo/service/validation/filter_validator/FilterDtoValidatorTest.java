package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.DataTypes;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
import demo.service.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static demo.service.validation.filter_validator.FilterErrors.INVALID_CRITERIA_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.INVALID_NUMBER_VALUE;
import static org.junit.jupiter.api.Assertions.*;

class FilterDtoValidatorTest {

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
                .setCriteria(DateCriteria.BEFORE.getKood())
                .setValue(Instant.now().toString());
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void validateDateValues_shouldReturnFalse_whenCriteriaNotValid() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.DATE)
                .setCriteria(NumberCriteria.SMALLER_THAN.getKood())
                .setValue(Instant.now().toString());
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());

        filterDto.setCriteria("invalidCriteria");
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());

        filterDto.setCriteria(null);
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());
    }


    @Test
    void validateDateValues_shouldReturnFalse_whenValueNotValid() {

    }


    @Test
    void validateNumeric_shouldReturnTrue() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.NUMBER)
                .setCriteria(NumberCriteria.SMALLER_THAN.getKood())
                .setValue("1");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void validateNumericCriteria_shouldReturnFalse() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.NUMBER)
                .setCriteria(NumberCriteria.SMALLER_THAN.getKood())
                .setValue("1");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void validateNumericValue_shouldReturnFalse() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.NUMBER)
                .setCriteria(NumberCriteria.SMALLER_THAN.getKood())
                .setValue("A");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_NUMBER_VALUE, result.getMessage());
    }
}