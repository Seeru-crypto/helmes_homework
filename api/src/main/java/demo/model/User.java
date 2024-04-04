package demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User extends AbstractAuditingEntity<Long> {
    public static final int USER_MAX_NAME_LENGTH = 200;
    public static final int USER_MIN_NAME_LENGTH = 2;
    public static final int MAX_EMAIL_LEN = 35;
    public static final int MAX_PHONE_LEN = 35;
    public static final String EMAIL_REGEX = "^[\\w!#$%&’*+\\/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+\\/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String PHONE_NR_REGEX = "\\+[0-9]{1,3} [0-9]{7,10}";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotNull
    @Size(min = USER_MIN_NAME_LENGTH)
    @Size(max = USER_MAX_NAME_LENGTH)
    private String name;

    @NotNull
    @AssertTrue
    @Column(name = "agree_terms", nullable = false)
    private Boolean agreeToTerms;

    @Column(unique = true)
    @Size(max = MAX_EMAIL_LEN, message = "Email is too long")
    @Email(regexp = EMAIL_REGEX, message = "INVALID_USER_EMAIL")
    private String email;

    @Column(name = "phone_number", unique = true)
    @Size(max = MAX_PHONE_LEN, message = "Phone is too long")
    @Pattern(regexp = PHONE_NR_REGEX, message = "INVALID_USER_PHONE_NUMBER")
    private String phoneNumber;

    @ManyToMany(fetch = LAZY,
            cascade = MERGE)
    @JoinTable(
            name = "user_sectors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    List<Sector> sectors = new ArrayList<>();
}
