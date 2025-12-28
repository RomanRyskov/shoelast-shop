package model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(name = "digital_price",precision = 10,scale = 2)
    private BigDecimal digitalPrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    private String category;
    private String imageUrl;

    //Фото с разных ракурсов
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    //Видео
    @Column(name = "video_url")
    private String videUrl;

    //3D viewer
    @Column(name = "viewer_url",length = 2000)
    private String viewer_url;
    @Column(name = "viewer_folder",length = 2000)
    private String viewerFolder;
    @Column(name = "viewer_embed_code", length = 2000)
    private String viewerEmbedCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductTipe productTipe;

    @OneToOne(mappedBy = "products",cascade = CascadeType.ALL)
    private DigitalProduct digitalProduct;

    @OneToMany(mappedBy = "products")
    private List<OrderItem> orderItem= new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime  updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public BigDecimal getPriceForType(ProductType type){
        if (type == ProductType.DIGITAL_3D && digitalPrice!=null){
            return digitalPrice;
        }
        return price;
    }
}
