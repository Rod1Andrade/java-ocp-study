import java.util.List;
import java.util.ArrayList;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class Welcome {

    private static String url;
    private static Option selectedOption;

    public static void main(String[] args) {
        if(args.length == 2) {
            selectedOption = Option.by(args[0]);
            url = args[1];
        }

        System.out.println("Iniciando Requisição: ");
        System.out.println(selectedOption.getDescription());
        System.out.println(url);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                                .uri(URI.create(url))
                                                .GET()
                                            .build();

        httpClient.sendAsync(httpRequest, BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenAccept(System.out::println)
                        .join();
    }

    private static String listOptions() {
        StringBuilder str = new StringBuilder();
        Option[] options = Option.allValues();

        for(Option option : options) {
            str.append(option.name() + ": " + option.getDescription());
        }

        return str.toString();
    }

}

enum Option {
    G("Fazer uma requisição <GET>"),
    P("Fazer uma requisição <POST>");

    private String description;

    Option(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public static Option[] allValues() {
        return Option.values();
    }

    public static Option by(String value) {
        for(Option o : allValues()) {
            if(o.name().equals(value)) {
                return o;
            }
        }

        throw new IllegalArgumentException("This options is not mapped");
    }
}