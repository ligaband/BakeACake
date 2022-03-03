package com.bakeacake.bakeacaketest.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private Integer id;
    private Integer user_id;
    private String tinSize;
    private String datePicker;
    private String deliveryTime;
    private String deliveryOptions;
    private String description;
    private String status;

    public Order(String tinSize, String datePicker,
                 String deliveryTime, String deliveryOptions, String description,
                 String status) {
        this.tinSize = tinSize;
        this.datePicker = datePicker;
        this.deliveryTime = deliveryTime;
        this.deliveryOptions = deliveryOptions;
        this.description = description;
        this.status = status;
    }

    public Order(Integer user_id, String tinSize, String datePicker, String deliveryTime, String deliveryOptions, String description, String status) {
        this.user_id = user_id;
        this.tinSize = tinSize;
        this.datePicker = datePicker;
        this.deliveryTime = deliveryTime;
        this.deliveryOptions = deliveryOptions;
        this.description = description;
        this.status = status;
    }
}
