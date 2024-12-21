package EvaRuiz.HealthCarer.model;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "images")
@Getter
@Builder
public class Image {

    @Id
    private String id;

    private String name;
    private String contentType;

    @Lob
    @Column(nullable = false, length = 60000000)//~50MB
    @Basic(fetch = FetchType.LAZY)
    private byte[] boxImage;

    public Image() {
    }

    public Image(String id, String name, String contentType, byte[] boxImage) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.boxImage = boxImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBoxImage() {
        return boxImage;
    }

    public void setBoxImage(byte[] boxImage) {
        this.boxImage = boxImage;
    }
}
