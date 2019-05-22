package se.ltu.kaicalib.core.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Publisher")
public class Publisher {

    @Id
    @GeneratedValue
    @Column(name = "publisher_id", updatable = false, nullable = false)
    private Long id;

    @Type(type="uuid-char")
    @Column(nullable=false, unique=true)
    final private UUID uuid = UUID.randomUUID();
}
