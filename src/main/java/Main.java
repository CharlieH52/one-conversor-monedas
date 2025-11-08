import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    protected static Dotenv env = Dotenv.load();
    protected static Gson json = new Gson();
    protected static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
//        Menu principal
        while (true) {
            String option;
            System.out.println("""
                    ### Bienvenido al sistema de conversion de monedas ###
                    Esta aplicacion fue desarrollada para el bootcamp: ORACLE NEXT GENERATION y ALURA.
                    
                    Selecciona la opcion ingresando su valor en la lista:
                    1. Conversor de moneda.
                    2. Ver codigos.
                    (e, E) Salir.
                    """);

            option = input.nextLine().trim().toLowerCase();
            boolean checks = checkInput(option);

            if (checks) {
                if (option.equals("1")) {
                    convertCurrency();
                    continue;
                }

                if (option.equals("2")) {
                    List<List<String>> codeList = getCodes();
                    for (List<String> code : codeList) {
                        System.out.println(code);
                    }
                    continue;
                }
            }

            if (option.equals("e")) {
                break;
            } else {
                System.out.println("Por favor, ingresa un valor valido.");
            }
        }
        input.close();
    }

    private static boolean checkInput(String input) {
        return !input.isEmpty();
    }

    private static void convertCurrency() {
        System.out.println("Ingresa el codigo de la moneda de cambio:");
        String exchangeCurrency = input.nextLine();
        System.out.println("Ingresa el codigo de tu moneda local:");
        String localCurrency = input.nextLine();
        System.out.println("Por favor, escribe el monto:");
        double amount = input.nextDouble();

        String endPoint = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f", env.get("API_KEY"), exchangeCurrency, localCurrency, amount);
        Map<String, Object> response = sendRequest(endPoint);

        double convertion = Double.parseDouble(response.get("conversion_rate").toString());
        double result = amount * convertion;

        System.out.println("El resultado es: " + result);
    }

    private static List<List<String>> getCodes() {
        String endPoint = String.format("https://v6.exchangerate-api.com/v6/%s/codes", env.get("API_KEY"));
        Map<String, Object> response = sendRequest(endPoint);
        return (List<List<String>>) response.get("supported_codes");
    }

    private static Map<String, Object> sendRequest(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return json.fromJson(response.body(), Map.class);
        } catch (InterruptedException e) {
            System.out.println(e);
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}