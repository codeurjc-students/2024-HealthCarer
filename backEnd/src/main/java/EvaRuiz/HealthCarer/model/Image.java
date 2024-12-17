package EvaRuiz.HealthCarer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    private String id;

    private String name;
    private String contentType;

    @Lob
    @Column
    @Basic(fetch = FetchType.LAZY)
    private byte[] boxImage;

}
