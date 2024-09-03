package oz.yamyam_map.batch.enums;

import lombok.Getter;

@Getter
public enum RestaurantSalesType {
	OPEN("영업"),
	CLOSED("폐업");

	private final String value;

	RestaurantSalesType(String value) {
		this.value = value;
	}
}
