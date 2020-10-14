package br.univali.portugol.plugin.maspath.conexao;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

public class ConexaoHTTP {
    
    private static String urlBase = "http://localhost:8080";

    public static JSONObject fazerRequest(String endpoint, String HTTPMethod, JSONObject parameter)
    {
        try {
            System.out.println("JSON "+parameter.toString());
            URL url =  new URL(urlBase+endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTPMethod);
            connection.setDoOutput(true);
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            byte[] out = parameter.toString().getBytes("UTF-8");
            int length = out.length;

            connection.setFixedLengthStreamingMode(length);
            try(OutputStream os = connection.getOutputStream()) {
                os.write(out);
            }
            connection.connect();
            InputStream response = connection.getInputStream();
            System.out.println("OI "+response.toString());
            JSONObject object = new JSONObject("{}");
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(e);
        }
    }
    
}
