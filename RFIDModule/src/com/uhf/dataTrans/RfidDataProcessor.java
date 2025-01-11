package com.uhf.dataTrans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.sun.xml.internal.ws.resources.SenderMessages;
import com.uhf.demo.UhfDemo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RfidDataProcessor {
    public void sendDataRegularly() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            try {
                int emptyChairNumber = collectData();
                String json = generateJson(emptyChairNumber);
                sendDataToServer(json);
            } catch (Exception e) {
                e.printStackTrace(); // 或使用日志记录异常
            }
        }, 0, 30, TimeUnit.MINUTES);
    }

    private int collectData() {
        ArrayList<ChairTag> tagList = UhfDemo.getCurrentTags();
        ConfigLoader config = new ConfigLoader();
        int emptyChair = 0;
        try {
            config.loadConfig();

            // 使用配置值
            String requiredPrefix = config.getEpcPrefix();
            int requiredRSSI = config.getRssiThreshold();
            int requiredCount = config.getCountThreshold();

            List<ChairTag> filteredChairTags = tagList.stream()
                    .filter(chairTag -> chairTag.getEPC().startsWith(requiredPrefix)
                            && chairTag.getRSSI() > requiredRSSI
                            && chairTag.getCount() > requiredCount)
                    .collect(Collectors.toList());
            emptyChair = filteredChairTags.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emptyChair;

    }

    private String generateJson(int emptyChairNumber) {
        LibraryInfo info = new LibraryInfo(emptyChairNumber);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
                    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                })
                .create();
        String json = gson.toJson(info);
        return json;
    }

    private void sendDataToServer(String json) throws IOException {
        URL url = new URL("https://localhost:8443/data");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read the response from input stream
        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

        connection.disconnect();
    }

//    public static void main(String[] args) throws IOException {
//        RfidDataProcessor pro = new RfidDataProcessor();
//        String json = pro.generateJson(pro.collectData());
//        pro.sendDataToServer(json);
//    }
}
