package com.example.zbd.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class WishItemKey implements Serializable {
    private Integer orderId;
    private Integer customerId;
}

