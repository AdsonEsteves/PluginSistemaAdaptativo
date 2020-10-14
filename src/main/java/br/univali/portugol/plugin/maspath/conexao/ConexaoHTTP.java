package br.univali.portugol.plugin.maspath.conexao;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class ConexaoHTTP {
    
    private static String urlBase = "http://localhost:8080";

    public static String fazerRequest(String endpoint, String HTTPMethod, JSONObject parameter)
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
            StringWriter writer = new StringWriter();
            IOUtils.copy(response, writer, "UTF-8");
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }
    
}
