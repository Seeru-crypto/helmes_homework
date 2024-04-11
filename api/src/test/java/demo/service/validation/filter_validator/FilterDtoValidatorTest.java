package demo.service.validation.filter_validator;

import demo.controller.dto.FilterDto;
import demo.service.filter.DataTypes;
import demo.service.filter.DateCriteria;
import demo.service.filter.NumberCriteria;
import demo.service.filter.StringCriteria;
import demo.service.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import static demo.service.validation.filter_validator.FilterErrors.INVALID_CRITERIA_ERROR;
import static demo.service.validation.filter_validator.FilterErrors.INVALID_DATE_VALUE;
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
    void validateDateValues_shouldReturnFalse_whenValueisNotValid() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.DATE)
                .setCriteria(DateCriteria.BEFORE.getKood())
                .setValue("abc");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_DATE_VALUE, result.getMessage());

        filterDto.setValue("123");
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_DATE_VALUE, result.getMessage());

        filterDto.setValue(null);
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_DATE_VALUE, result.getMessage());
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
    void validateNumeric_shouldReturnFalse_whenCriteriaInvalid() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.NUMBER)
                .setCriteria(DateCriteria.BEFORE.getKood())
                .setValue("2");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());

        filterDto.setCriteria("invalidCriteria");
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());
    }

    @Test
    void validateNumeric_shouldReturnFalse_whenValueInvalid() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.NUMBER)
                .setCriteria(NumberCriteria.SMALLER_THAN.getKood())
                .setValue("2as");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_NUMBER_VALUE, result.getMessage());

        filterDto.setValue(null);
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_NUMBER_VALUE, result.getMessage());
    }

    @Test
    void validateString_shouldReturnTrue() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.STRING)
                .setCriteria(StringCriteria.CONTAINS.getKood())
                .setValue("1");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertTrue(result.isValid());
        assertNull(result.getMessage());
    }

    @Test
    void validateString_shouldReturnFalse_whenCriteriaInvalid() {
        FilterDto filterDto = new FilterDto()
                .setType(DataTypes.STRING)
                .setCriteria(DateCriteria.BEFORE.getKood())
                .setValue("as2");
        ValidationResult result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());

        filterDto.setCriteria("invalidCriteria");
        result = filterDtoValidator.validate(filterDto);
        assertFalse(result.isValid());
        assertEquals(INVALID_CRITERIA_ERROR, result.getMessage());
    }

}