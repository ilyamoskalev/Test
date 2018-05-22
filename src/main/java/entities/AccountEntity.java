package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    public AccountEntity() {
    }

    public AccountEntity(String id) {
        this.id = id;
    }
}
