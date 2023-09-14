package shared.dto.pricing;

import java.util.List;

public class PricingErrorDto {
    private Long orderId;
    private List<PricingErrorDetailDto> errorDetails;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<PricingErrorDetailDto> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(final List<PricingErrorDetailDto> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
