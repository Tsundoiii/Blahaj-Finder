package BlahajFinder;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * Class to represent JSON response containing information about stocks of
 * shonks at each IKEA store.
 * Only necessary fields from the response are parsed.
 */
public class Availabilities {
    public static class AvailabilityInfo {
        public static class AvailableStock {
            @Key
            private int quantity;

            public int getQuantity() {
                return quantity;
            }
        }

        public static class ClassUnitKey {
            @Key
            private String classUnitCode;

            public String getClassUnitCode() {
                return classUnitCode;
            }
        }

        @Key
        private ClassUnitKey classUnitKey;
        @Key
        private List<AvailableStock> availableStocks;

        public ClassUnitKey getClassUnitKey() {
            return classUnitKey;
        }

        public List<AvailableStock> getAvailableStocks() {
            return availableStocks;
        }
    }

    @Key
    private List<AvailabilityInfo> data;

    public List<AvailabilityInfo> getData() {
        return data;
    }
}
