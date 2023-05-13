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
    public static void main(String[] args) {
        try {
            HttpRequest request = new NetHttpTransport().createRequestFactory((HttpRequest hr) -> {
                hr.setParser(new JsonObjectParser(new GsonFactory()));
            }).buildGetRequest(new GenericUrl("https://www.ikea.com/us/en/meta-data/navigation/stores-detailed.json"));
            HttpResponse response = request.execute();
            Type type = new TypeToken<List<Store>>() {}.getType();
            List<Store> stores = (List<Store>) response.parseAs(type);
            for (Store store : stores) {
                System.out.println(store.getPlaceId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}