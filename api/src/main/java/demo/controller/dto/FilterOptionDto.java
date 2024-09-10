package demo.controller.dto;

import demo.service.filter.FieldType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FilterOptionDto {
    private String field;

    private List<String> criteriaValues;

    private FieldType fieldType;
}
