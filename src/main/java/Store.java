import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import com.google.api.client.util.Key;

public class Store {
    public static class Address {
        @Key
        private String timezone;
        @Key
        private String street;
        @Key
        private String city;
        @Key
        private String stateProvinceCode;
        @Key
        private String zipCode;

        public String getTimezone() {
            return timezone;
        }

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

    public void printInfo(int quantity, boolean showAddress, boolean showOperatingHours) {
        System.out.println((quantity > 0 ? Color.GREEN.getColorCode() : Color.RED.getColorCode()) + quantity
                + Color.RESET.getColorCode() + " blåhaj(s) are available at " + getName());
        if (showAddress) {
            System.out.println(address);
            System.out.println();
        }
        if (showOperatingHours) {
            System.out.println("Store hours:");
            hours.normal.forEach(operatingHours -> {
                ZonedDateTime now;
                /*
                 * Check if the store is the IKEA in Jacksonville, FL and if it is set the time
                 * zone manually
                 * 
                 * I have do to this because for some reason the API doesn't return timezone
                 * information for that particular store even though LITERALLY EVERY OTHER STORE
                 * has timezone info
                 * 
                 * I feel like I should report this as a bug but even if I did want to I have no
                 * idea where to even report this to
                 * 
                 * I guess Jacksonville is just above earthly things such as time zones
                 */
                if (id.equals("537")) {
                    now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York"));
                } else {
                    now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of(address.getTimezone()));
                }
                int currentHour = now.getHour();
                DayOfWeek currentDay = now.getDayOfWeek();
                boolean open = Integer
                        .parseInt(hours.getNormal().get(currentDay.getValue() - 1).getOpen().substring(0,
                                2)) <= currentHour
                        && currentHour < Integer
                                .parseInt(hours.getNormal().get(currentDay.getValue() - 1).getClose().substring(0, 2));

                System.out.println(
                        (currentDay.getDisplayName(TextStyle.SHORT, Locale.getDefault()).equalsIgnoreCase(
                                operatingHours.getDay())
                                        ? (open ? Color.GREEN.getColorCode() : Color.RED.getColorCode()) + "* "
                                                + Color.RESET.getColorCode()
                                        : "  ")
                                + operatingHours);
            });
        }
    }
}
