package shared.constants;

import java.util.List;

public enum OrderBeerStatus {
    PRICING_FAIL, INVENTORY_UNAVAILABLE, INVENTORY_INCOMPLETE, MULTI_FAIL, VALID;

    private static final List<OrderBeerStatus> invalidStatuses =
        List.of(PRICING_FAIL, INVENTORY_UNAVAILABLE, MULTI_FAIL);

    private static final List<OrderBeerStatus> incompleteStatuses =
        List.of(INVENTORY_INCOMPLETE);

    public static boolean isInvalid(final OrderBeerStatus status) {
        return invalidStatuses.contains(status);
    }

    public static boolean isIncomplete(final OrderBeerStatus status) {
        return incompleteStatuses.contains(status);
    }
}
