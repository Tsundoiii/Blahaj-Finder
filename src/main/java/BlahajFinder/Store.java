package BlahajFinder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import com.google.api.client.util.Key;

/**
 * Class to repsent JSON response containing information about each IKEA store,
 * Only necessary fields from the response are parsed.
 * Helper methods are also present.
 */
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

        public String getState() {
            return stateProvinceCode.substring(2);
        }

        public String toString() {
            return street + "\n" + city + ", " + getState() + " " + zipCode;
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

        public String getCode() {
            return code;
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

    /**
     * Calculate the distance from specified coordinates to this IKEA store using
     * the haversine formula
     * 
     * @param coordinates Array of coordinates in decimal degrees
     * @return Distance away from coordinates in kilometers
     */
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

    /**
     * Print specified information about this IKEA store
     * 
     * @param quantity        Quantity of shonks available at this IKEA store
     * @param printAddress    Print address of store if true; determined by
     *                        {@code -a} flag
     * @param printStoreHours Print opening hours of store if true; determined by
     *                        {@code -o} flag
     */
    public void printInfo(int quantity, boolean printAddress, boolean printStoreHours) {
        System.out.println((quantity > 0 ? Color.GREEN.getColorCode() : Color.RED.getColorCode()) + quantity
                + Color.RESET.getColorCode() + " blåhaj(s) are available at " + getName());
        System.out.println();
        if (printAddress) {
            System.out.println("Address:");
            System.out.println(address);
            System.out.println();
        }
        if (printStoreHours) {
            System.out.println("Store hours:");
            hours.normal.forEach(operatingHours -> {
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
                ZonedDateTime now = id.equals("537")
                        ? ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York"))
                        : ZonedDateTime.now().withZoneSameInstant(ZoneId.of(address.getTimezone()));

                /*
                 * pls ignore this abomination
                 * this was an experiment to see how much I could fit into "one" line
                 * (the answer is too much)
                 */
                System.out.println(
                        (now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault()).equalsIgnoreCase(
                                operatingHours.getDay())
                                        ? ((Integer
                                                .parseInt(hours.getNormal().get(now.getDayOfWeek().getValue() - 1)
                                                        .getOpen()
                                                        .substring(0,
                                                                2)) <= now.getHour()
                                                && now.getHour() < Integer
                                                        .parseInt(
                                                                hours.getNormal().get(now.getDayOfWeek().getValue() - 1)
                                                                        .getClose().substring(0, 2)))
                                                                                ? Color.GREEN.getColorCode()
                                                                                : Color.RED.getColorCode())
                                                + "* "
                                                + Color.RESET.getColorCode()
                                        : "  ")
                                + operatingHours);
            });
            System.out.println();
        }
    }
}
