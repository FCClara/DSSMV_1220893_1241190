package com.example.projectdroid_1220893_1241190.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PrecoService {

    private final OkHttpClient client = new OkHttpClient();

    // Open Food Facts asks for a custom User-Agent
    private static final String USER_AGENT = "ProjectDroid/1.0 (your@email.com)";

    /** Step 1: search Open Food Facts by name, return the first barcode found (or null). */
    public String procurarCodigoBarras(String nome) throws IOException {
        android.util.Log.d("CODIGOBARRAS", "Procurando por: " + nome);
        String url = "https://world.openfoodfacts.org/cgi/search.pl"
                + "?search_terms=" + URLEncoder.encode(nome, "UTF-8")
                + "&search_simple=1&action=process&json=1"
                + "&page_size=5&fields=code,product_name";

        JsonObject json = get(url);
        JsonArray products = json.getAsJsonArray("products");
        if (products == null || products.size() == 0) return null;
        android.util.Log.d("BAR_CODE", "barcode: " + products);
        return products.get(0).getAsJsonObject().get("code").getAsString();
    }

    /** Step 2: get prices from Open Prices for a barcode and average them (EUR only). Returns null if none. */
    public Double mediaPreco(String codigoBarras) throws IOException {
        String url = "https://prices.openfoodfacts.org/api/v1/prices"
                + "?product_code=" + codigoBarras
                + "&currency=EUR&size=50";

        JsonObject json = get(url);
        JsonArray items = json.getAsJsonArray("items");
        if (items == null || items.size() == 0) return null;

        double soma = 0;
        int n = 0;
        for (JsonElement el : items) {
            JsonElement p = el.getAsJsonObject().get("price");
            if (p != null && !p.isJsonNull()) {
                soma += p.getAsDouble();
                n++;
            }
        }
        return n == 0 ? null : soma / n;
    }

    private JsonObject get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("HTTP " + response.code());
            }
            return JsonParser.parseString(response.body().string()).getAsJsonObject();
        }
    }
}