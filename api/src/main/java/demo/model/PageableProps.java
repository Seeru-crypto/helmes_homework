package demo.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableProps {
  String sortBy;
  Integer pageNumber;
  Integer sizeOfPage;
}
