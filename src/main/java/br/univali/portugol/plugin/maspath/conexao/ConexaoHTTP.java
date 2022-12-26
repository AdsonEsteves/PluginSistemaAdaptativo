package br.univali.portugol.plugin.maspath.conexao;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.io.IOUtils;

public class ConexaoHTTP {

    private static String urlBase = "http://localhost:8080";

    public static String fazerRequest(String endpoint, String HTTPMethod, JsonNode parameter) {
        try {

            URL url = new URL(urlBase + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTPMethod);
            connection.setDoOutput(true);
            // connection.setReadTimeout(10000);
            if (parameter != null) {
                connection.setRequestProperty("accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");

                System.out.println("JSON " + parameter.toString());
                byte[] out = parameter.toString().getBytes("UTF-8");
                int length = out.length;

                connection.setFixedLengthStreamingMode(length);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(out);
                }
            }
            connection.connect();
            InputStream response = connection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(response, writer, "UTF-8");
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }

}
