import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.reflect.TypeToken;

public class Main {
    public static HttpResponse request(String url, String[]... headers) throws IOException {
        HttpRequest request = new NetHttpTransport().createRequestFactory((HttpRequest hr) -> {
            hr.setParser(new JsonObjectParser(new GsonFactory()));
        }).buildGetRequest(new GenericUrl(url));
        for (String[] header : headers) {
            request.getHeaders().set(header[0], header[1]);
        }

        return request.execute();
    }

    public static double distance(double[] c1, double[] c2) {
        final int R = 6371;
        final double lat1 = Math.toRadians(c1[0]);
        final double lat2 = Math.toRadians(c2[0]);
        final double deltaLatitude = Math.toRadians(c2[0] - c1[0]);
        final double deltaLongitude = Math.toRadians(c2[1] - c1[1]);

        final double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLongitude / 2), 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public static void main(String[] args) throws IOException {
        @SuppressWarnings("unchecked") // this is a communist state, no dissent is allowed
        List<Store> stores = (List<Store>) request(
                "https://www.ikea.com/us/en/meta-data/navigation/stores-detailed.json")
                .parseAs(new TypeToken<List<Store>>() {
                }.getType());
        Availabilities availabilities = request(
                "https://api.ingka.ikea.com/cia/availabilities/ru/us?itemNos=90373590&expand=StoresList,Restocks,SalesLocations,",
                new String[] { "x-client-id", "da465052-7912-43b2-82fa-9dc39cdccef8" })
                .parseAs(Availabilities.class);

        SortedMap<Double, Store> distToStores = new TreeMap<Double, Store>();
        double[] cd = { 34.13769217402672, -118.05555567055725 };
        for (Store store : stores) {
            if (store.getBuClassification().getCode().equals("STORE")) { // only include stores and not other locations
                                                                         // like Plan and Order Points
                distToStores.put(distance(cd, store.getCoordinates()), store);
            }
        }
        System.out.println(distToStores.values().stream().findFirst().get());
    }
}