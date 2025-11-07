import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    protected static Dotenv env = Dotenv.load();
    protected static Gson json = new Gson();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Escribe la moneda objetivo:");
        String mainCurrency = input.nextLine();
        System.out.println("Escribe tu moneda:");
        String targetCurrency = input.nextLine();
        System.out.println("Escribe el monto:");
        double amount = input.nextDouble();

        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f", env.get("API_KEY"), mainCurrency, targetCurrency, amount);
        System.out.println("ENDPOINT>> " + url);

        Map<String, Object> response = sendRequest(url);
        System.out.println(response);
    }

    private static Map<String, Object> sendRequest(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Map<String, Object> myjson = json.fromJson(response.body(), Map.class);
            return myjson;
        } catch (InterruptedException e) {
            System.out.println(e.toString());
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}