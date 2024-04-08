package demo.service.validation;

public interface Validator<T> {
  ValidationResult validate(T entity);
}
