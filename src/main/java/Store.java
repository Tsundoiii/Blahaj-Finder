import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

import com.google.api.client.util.Key;

public class Store {
    public static class Address {
        @Key
        private String street;
        @Key
        private String city;
        @Key
        private String stateProvinceCode;
        @Key
        private String zipCode;

        public String toString() {
            return street + "\n" + city + ", " + stateProvinceCode.substring(2) + " " + zipCode;
        }
    }

    public static class Hours {
        public static class OperatingHours {
            @Key
            private String day;
            @Key
            private String open;
            @Key
            private String close;

            public String getDay() {
                return day;
            }

            public String getOpen() {
                return open;
            }

            public String getClose() {
                return close;
            }

            public String toString() {
                return day + ": " + open + "-" + close;
            }
        }

        @Key
        private List<OperatingHours> normal;

        public List<OperatingHours> getNormal() {
            return normal;
        }
    }

    public static class BuClassification {
        @Key
        private String code;
        @Key
        private String name;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    @Key
    private String id;
    @Key
    private String name;
    @Key
    private String lat;
    @Key
    private String lng;
    @Key
    private Address address;
    @Key
    private Hours hours;
    @Key
    private BuClassification buClassification;
    @Key
    private String placeId;

    public String getId() {
        return id;
    }

    public String getName() {
        return Color.YELLOW.getColorCode() + name + Color.RESET.getColorCode();
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public Address getAddress() {
        return address;
    }

    public Hours getHours() {
        return hours;
    }

    public BuClassification getBuClassification() {
        return buClassification;
    }

    public String getPlaceId() {
        return placeId;
    }

    public double[] getCoordinates() {
        return new double[] { Double.parseDouble(lat), Double.parseDouble(lng) };
    }

    public double distance(double[] coordinates) {
        final double[] storeCoordinates = getCoordinates();
        final int R = 6371;
        final double lat1 = Math.toRadians(storeCoordinates[0]);
        final double lat2 = Math.toRadians(coordinates[0]);
        final double deltaLatitude = Math.toRadians(coordinates[0] - storeCoordinates[0]);
        final double deltaLongitude = Math.toRadians(coordinates[1] - storeCoordinates[1]);

        final double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLongitude / 2), 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public void printInfo(int quantity) {
        int currentHour = LocalDateTime.now().get(ChronoField.HOUR_OF_DAY);
        boolean open = Integer.parseInt() <= currentHour < ;
        System.out.println(currentHour);
        System.out.println(Color.YELLOW.getColorCode() + name + Color.RESET.getColorCode());
        System.out.println(address);
        System.out.println();
        hours.normal.forEach(operatingHours -> System.out.println(operatingHours));
    }
}
