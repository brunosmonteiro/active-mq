package shared.constants.inventory;

import java.util.List;

public enum InventoryValidationStatus {
    VALID, PARTIALLY_MISSING, MISSING;

    private static final List<InventoryValidationStatus> incompleteStatusList =
        List.of(PARTIALLY_MISSING, MISSING);

    public static boolean incompleteStatus(final InventoryValidationStatus status) {
        return incompleteStatusList.contains(status);
    }
}
