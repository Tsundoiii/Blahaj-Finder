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
    @Option(names = { "-h", "--hours" }, description = "show store hours")
    private boolean storeHours = false;

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
        HashMap<String, Integer> availabilities = new HashMap<String, Integer>();
        SortedMap<Double, Store> distToStores = new TreeMap<Double, Store>();

        try {
            @SuppressWarnings("unchecked") // this is a communist state, no dissent is allowed
            List<Store> stores = ((List<Store>) request(
                    "https://www.ikea.com/us/en/meta-data/navigation/stores-detailed.json")
                    .parseAs(new TypeToken<List<Store>>() {
                    }.getType())).stream()
                    .filter(store -> store.getBuClassification().getCode().equals("STORE") && (states != null
                            ? states.stream()
                                    .anyMatch(state -> ("US" + state).equals(store.getAddress().getStateProvinceCode()))
                            : true))
                    .collect(Collectors.toList());
            stores.forEach(store -> distToStores.put(store.distance(coordinates), store));
            request(
                    "https://api.ingka.ikea.com/cia/availabilities/ru/us?itemNos=90373590&expand=StoresList,Restocks,SalesLocations,",
                    new String[] { "x-client-id", "da465052-7912-43b2-82fa-9dc39cdccef8" })
                    .parseAs(Availabilities.class).getData().stream()
                    .filter(availabilityInfo -> availabilityInfo.getAvailableStocks() != null)
                    .forEach(availabilityInfo -> availabilities.put(
                            availabilityInfo.getClassUnitKey().getClassUnitCode(),
                            availabilityInfo.getAvailableStocks().get(0).getQuantity()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println(Color.CYAN.getColorCode() + "BLÅHAJ FINDER" + Color.RESET.getColorCode());
        System.out.println();
        for (double entry : distToStores.keySet().stream().limit(numberOfStores).collect(Collectors.toList())) {
            Store store = distToStores.get(entry);
            store.printInfo(availabilities.get(store.getId()), address, storeHours);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new BlahajFinder()).execute(args));
    }
}