package com.sushi.wasabi.dto;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import com.sushi.wasabi.enums.DiscountType;
    import lombok.Getter;
import lombok.Setter;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.util.List;

@Getter
@Setter
public class DiscountAdminRequest {
    private String title;
    private String description;
    private DiscountType type;
    private BigDecimal value;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startsAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endsAt;

    private BigDecimal minOrderValue;
    private boolean stackable;

    private List<Integer> productIds;

}
