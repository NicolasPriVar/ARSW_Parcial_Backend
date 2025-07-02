package com.escuelaing.parcial.adapter;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Component
public class ParcialAdapter implements AlphaAdapter{
    @Value("${api.alpha.apiToken}")
    private String apikey;

    @Value("${api.chatgpt.url}")
    private String endpoint;

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public String generateResponse(String input) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apikey)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            if (json.has("error")) {
                JsonObject error = json.getAsJsonObject("error");
                String mensaje = error.has("message") ? error.get("message").getAsString() : "Error desconocido.";
                return "Error de OpenAI: " + mensaje;            }


            return json.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

        } catch (Exception e) {
            return "Error al conectar con Alphavantage: " + e.getMessage();
        }
    }
}
