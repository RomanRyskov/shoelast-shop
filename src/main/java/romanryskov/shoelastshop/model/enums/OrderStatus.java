package romanryskov.shoelastshop.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Ожидает подтверждения"),
    CONFIRMED("Подтвержден"),
    PROCESSING("В обработке"),
    SHIPPED("Отправлен"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменен");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}