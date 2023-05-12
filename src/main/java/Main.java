import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Main {
    public static void main(String[] args) {
        try {
            HttpRequest request = new NetHttpTransport().createRequestFactory().buildGetRequest(new GenericUrl("https://www.ikea.com/us/en/meta-data/navigation/stores-detailed.json"));
            HttpResponse response = request.execute();
            String stores = response.parseAsString();
            System.out.println(stores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}