import java.util.List;

import com.google.api.client.util.Key;

public class Availabilities {
    public static class AvailabilityInfo {
        public static class AvailableStock {
            public static class Probability {
                public static class Communication {
                    @Key
                    private String messageType;

                    public String getMessageType() {
                        return messageType;
                    }
                }

                @Key
                private Communication communcation;

                public Communication getCommuncation() {
                    return communcation;
                }
            }

            @Key
            private int quantity;
            @Key
            private String updateDateTime;
            @Key
            private List<Probability> probabilities;

            public int getQuantity() {
                return quantity;
            }

            public String getUpdateDateTime() {
                return updateDateTime;
            }

            public List<Probability> getProbabilities() {
                return probabilities;
            }
        }

        public static class ClassUnitKey {
            @Key
            private String classUnitCode;
            @Key
            private String classUnitType;

            public String getClassUnitCode() {
                return classUnitCode;
            }

            public String getClassUnitType() {
                return classUnitType;
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
    @Key
    private String timestamp;

    public List<AvailabilityInfo> getData() {
        return data;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
