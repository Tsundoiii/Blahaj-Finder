import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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

    public static double distance(double[] c, double[] d) {
        return Math.hypot(d[0] - c[0], d[1] - c[1]);
    }

    public static void main(String[] args) {
        try {
            Type type = new TypeToken<List<Store>>() {
            }.getType();
            List<Store> stores = (List<Store>) request(
                    "https://www.ikea.com/us/en/meta-data/navigation/stores-detailed.json").parseAs(type);
            Availabilities availabilities = request(
                    "https://api.ingka.ikea.com/cia/availabilities/ru/us?itemNos=90373590&expand=StoresList,Restocks,SalesLocations,",
                    new String[] { "x-client-id", "da465052-7912-43b2-82fa-9dc39cdccef8" })
                    .parseAs(Availabilities.class);
                    System.out.println(distance(new double[] {-3, -1}, new double[] {-7, -1}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}