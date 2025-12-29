package romanryskov.shoelastshop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // URL фото

    @Column(name = "thumbnail_url")
    private String thumbnailUrl; // URL миниатюры (опционально)

    @Column(name = "view_angle")
    private String viewAngle; // Ракурс: "front", "side", "top", "back", "detail" и т.д.

    @Column(name = "display_order")
    private Integer displayOrder; // Порядок отображения (1, 2, 3...)

    @Column(name = "alt_text")
    private String altText; // Альтернативный текст для SEO

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (displayOrder == null) {
            displayOrder = 0;
        }
    }
}