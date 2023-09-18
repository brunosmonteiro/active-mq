package shared.dto.beer;

public class BeerRegistryDto {
    private String externalId;
    private String name;
    private String description;

    public BeerRegistryDto() {
    }

    public BeerRegistryDto(final String externalId, final String name, final String description) {
        this.externalId = externalId;
        this.name = name;
        this.description = description;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
