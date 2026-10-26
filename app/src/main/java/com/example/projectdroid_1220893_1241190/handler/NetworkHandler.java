package com.example.projectdroid_1220893_1241190.handler;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.ApiConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkHandler {

    public static String get(Context ctx, String path) throws IOException {
        return request(ctx, "GET", path, null);
    }

    public static String post(Context ctx, String path, String body) throws IOException {
        return request(ctx, "POST", path, body);
    }

    public static String put(Context ctx, String path, String body) throws IOException {
        return request(ctx, "PUT", path, body);
    }

    public static String delete(Context ctx, String path) throws IOException {
        return request(ctx, "DELETE", path, null);
    }

    private static String request(Context ctx, String method, String path, String body)
            throws IOException {
        URL url = new URL(ApiConfig.getBaseUrl(ctx) + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setRequestMethod(method);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("x-apikey", ApiConfig.getApiKey(ctx));

            if (body != null) {
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(body.getBytes(StandardCharsets.UTF_8));
                }
            }

            int code = conn.getResponseCode();
            if (code < 200 || code >= 300) {
                throw new IOException("HTTP " + code + " em " + method + " " + url);
            }

            return readString(conn.getInputStream());
        } finally {
            conn.disconnect();
        }
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}