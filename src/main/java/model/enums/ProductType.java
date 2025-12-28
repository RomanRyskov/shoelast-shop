package model.enums;


import lombok.Getter;

@Getter
public enum ProductType {
    PHYSICAL("Физическая модель", "Изготовленная из пластика, требует доставки"),
    DIGITAL_3D("3D модель", "Цифровая модель для скачивания");

    private final String name;
    private final String description;

    ProductType(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
