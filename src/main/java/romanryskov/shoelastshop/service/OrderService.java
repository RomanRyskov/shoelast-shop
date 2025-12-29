package romanryskov.shoelastshop.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import romanryskov.shoelastshop.model.entity.Order;
import romanryskov.shoelastshop.model.entity.OrderItem;
import romanryskov.shoelastshop.model.entity.OrderItemDownload;
import romanryskov.shoelastshop.model.entity.Product;
import romanryskov.shoelastshop.model.enums.OrderStatus;
import romanryskov.shoelastshop.model.enums.ProductType;
import romanryskov.shoelastshop.repository.OrderItemDownloadRepository;
import romanryskov.shoelastshop.repository.OrderItemRepository;
import romanryskov.shoelastshop.repository.OrderRepository;
import romanryskov.shoelastshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemDownloadRepository orderItemDownloadRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, OrderItemDownloadRepository orderItemDownloadRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemDownloadRepository = orderItemDownloadRepository;
    }

    @Transactional
    public Order createOrder(Order order, List<OrderItem> items) {
        if (order == null) {
            throw new IllegalArgumentException("order cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("items cannot be null or empty");
        }
        if (order.getUser() == null) {
            throw new IllegalArgumentException("user cannot be null");
        }

        List<OrderItem> orderItemsToSave = new ArrayList<OrderItem>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem item : items) {
            if (item.getProduct() == null || item.getProduct().getId() == null) {
                throw new IllegalArgumentException("product cannot be null");
            }
            //Проверяем товар в базе по id
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("product not found"));
            //Проверяем кол-во товара в заказе
            Integer requestedQuantity = item.getQuantity();
            if (requestedQuantity <= 0) {
                throw new IllegalArgumentException("quantity must be greater than 0");
            }
            //Проверяем остаток на складе если модель физическая
            if(product.getProductType() == ProductType.PHYSICAL){
                Integer stockQuantity = product.getStockQuantity();
                if(stockQuantity == null || stockQuantity < requestedQuantity){
                    throw new IllegalArgumentException(
                            "Недостаточно товара на складе. Товар: " + product.getName() +
                            ", запрошено: " + requestedQuantity +
                            ", доступно" + (stockQuantity == null ? 0 :stockQuantity)
                    );
                }
            }
            //Определяем тип и цену
            ProductType productType = product.getProductType();
            BigDecimal itemPrice = product.getPriceForType(productType);
            //Создаем OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setProductType(productType);
            orderItem.setPrice(itemPrice);
             //Если цифровой товар создаем возможность загрузки
            if(productType == ProductType.DIGITAL_3D){
                OrderItemDownload download = createDowloadForDigitalProduct(product,orderItem);
                orderItem.setDownload(download);
            }
            //общая сумма
            BigDecimal itemTotal = itemPrice.multiply(BigDecimal.valueOf(requestedQuantity));
            totalPrice = totalPrice.add(itemTotal);

            orderItemsToSave.add(orderItem);

            if(productType == ProductType.PHYSICAL){
                product.setStockQuantity(product.getStockQuantity() - requestedQuantity);
                productRepository.save(product);
            }
        }
        order.setTotalPrice(totalPrice);

        if(order.getStatus() == null){
            order.setStatus(OrderStatus.PENDING);
        }
        Order savedOrder = orderRepository.save(order);

        for(OrderItem orderItem : orderItemsToSave){
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }
        savedOrder.setOrderItems(orderItemsToSave);

        return savedOrder;
    }


}
