package BlahajFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.reflect.TypeToken;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class BlahajFinder implements Runnable {
    @Option(names = { "-c", "--coordinates" }, description = "coordinates", split = ",")
    private double[] coordinates = { 0, 0 };
    @Option(names = { "-s", "--state" }, description = "state to search in", split = ",")
    private ArrayList<String> states;
    @Option(names = { "-n", "--number-of-stores" }, description = "number of stores to list")
    private int numberOfStores = 1;
    @Option(names = { "-a", "--address" }, description = "show address")
    private boolean address = false;
    @Option(names = { "-o", "--opening-hours" }, description = "show opening hours")
    private boolean storeHours = false;

    /**
     * Make an HTTP request to a URL with the specified headers and parse the
     * response as JSON
     * 
     * @param url     URL to make request to
     * @param headers String arrays of header name and value pairs
     * @return HTTP response
     * @throws IOException
     */
    public static HttpResponse request(String url, String[]... headers) throws IOException {
        HttpRequest request = new NetHttpTransport()
                .createRequestFactory(hr -> hr.setParser(new JsonObjectParser(new GsonFactory())))
                .buildGetRequest(new GenericUrl(url));
        for (String[] header : headers) {
            request.getHeaders().set(header[0], header[1]);
        }

        return request.execute();
    }

    @Override
    public void run() {
        /*
         * Map of IKEA store codes to availability of blahajs at that store
         */
        HashMap<String, Integer> availabilities = new HashMap<String, Integer>();
        /*
         * Map of distances between provided coordinates and each IKEA store.
         * 
         * Since this a sorted map, stores become ordered based on how close they are to
         * provided coordinates no matter what ordered they are added to the map in.
         */
        SortedMap<Double, Store> distanceToStores = new TreeMap<Double, Store>();

        try {
            @SuppressWarnings("unchecked") // this is a communist state, no dissent is allowed
            List<Store> stores = ((List<Store>) request(
                    "https://www.ikea.com/us/en/meta-data/navigation/stores-detailed.json")
                    .parseAs(new TypeToken<List<Store>>() {
                    }.getType())).stream()
                    .filter(store -> store.getBuClassification().getCode().equals("STORE") && (states != null
                            ? states.contains(store.getAddress().getState())
                            : true))
                    .collect(Collectors.toList());
            stores.forEach(store -> distanceToStores.put(store.distance(coordinates), store));
            request(
                    "https://api.ingka.ikea.com/cia/availabilities/ru/us?itemNos=90373590&expand=StoresList,Restocks,SalesLocations,",
                    new String[] { "x-client-id", "da465052-7912-43b2-82fa-9dc39cdccef8" }) // API key taken from IKEA
                                                                                            // website,
                                                                                            // may change in future
                    .parseAs(Availabilities.class).getData().stream()
                    .filter(availabilityInfo -> availabilityInfo.getAvailableStocks() != null)
                    .forEach(availabilityInfo -> availabilities.put(
                            availabilityInfo.getClassUnitKey().getClassUnitCode(),
                            availabilityInfo.getAvailableStocks().get(0).getQuantity()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        for (double entry : distanceToStores.keySet().stream().limit(numberOfStores).collect(Collectors.toList())) {
            Store store = distanceToStores.get(entry);
            store.printInfo(availabilities.get(store.getId()), address, storeHours);
        }
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new BlahajFinder()).execute(args));
    }
}