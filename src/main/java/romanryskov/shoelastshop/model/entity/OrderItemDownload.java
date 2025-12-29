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
@Table(name = "order_item_downloads")
public class OrderItemDownload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_item_id", nullable = false, unique = true)
    private OrderItem orderItem;

    @Column(name ="download_url", nullable = false)
    private String downloadUrl;

    @Column(name = "download_token", nullable = false, unique = true)
    private String downloadToken;

    @Column(name = "download_expires_at")
    private LocalDateTime downloadExpiresAt;

    @Column(name = "max_downloads")
    private Integer maxDownloads = 5;

    @Column(name = "download_count")
    private  Integer downloadCount = 0;

    @Column(name = "is_activated")
    private Boolean isActivated = false;

    @Column(name = "activated_at")
    private  LocalDateTime activatedAt;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (downloadToken == null) {
            downloadToken = UUID.randomUUID().toString();
        }
        // срок действия 30 дней
        if (downloadExpiresAt == null) {
            downloadExpiresAt = LocalDateTime.now().plusDays(30);
        }
    }

    public boolean isExpired() {
        return downloadExpiresAt != null &&
                LocalDateTime.now().isAfter(downloadExpiresAt);
    }

    public boolean canDownload() {
        return isActivated &&
                !isExpired() &&
                downloadCount < maxDownloads;
    }
}