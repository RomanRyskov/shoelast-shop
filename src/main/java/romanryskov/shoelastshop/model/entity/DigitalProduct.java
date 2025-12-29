package romanryskov.shoelastshop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DigitalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "file_url", nullable = false)
    private String fileUrl; // URL для скачивания файла (S3, CDN, или локальный путь)

    @Column(name = "file_name")
    private String fileName; // Имя файла (например: "shoe_last_42.stl")

    @Column(name = "file_size")
    private Long fileSize; // Размер файла в байтах

    @Column(name = "file_format")
    private String fileFormat; // Формат файла (STL, OBJ, 3MF и т.д.)

    @Column(name = "download_token")
    private String downloadToken; // Уникальный токен для безопасного скачивания

    @Column(name = "download_expires_at")
    private LocalDateTime downloadExpiresAt; // Срок действия ссылки

    @Column(name = "max_downloads")
    private Integer maxDownloads; // Максимальное количество скачиваний

    @Column(name = "download_count")
    private Integer downloadCount = 0; // Текущее количество скачиваний

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (downloadToken == null) {
            downloadToken = generateDownloadToken();
        }
    }

    private String generateDownloadToken() {
        // Генерация уникального токена (UUID или другой метод)
        return UUID.randomUUID().toString();
    }
}
