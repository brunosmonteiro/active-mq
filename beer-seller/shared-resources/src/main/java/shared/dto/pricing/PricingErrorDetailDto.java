package shared.dto.pricing;

public class PricingErrorDetailDto {
    private Long beerId;
    private Integer requestedQuantity;
    private String errorMessage;

    public Long getBeerId() {
        return beerId;
    }

    public void setBeerId(final Long beerId) {
        this.beerId = beerId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(final Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
