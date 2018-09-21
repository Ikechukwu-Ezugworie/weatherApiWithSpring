package com.bw.weatherApi.weatherApi.utils;

import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

public class WeatherApiUtils {

    public static OkHttpClient okHttpClient = new OkHttpClient();

    public static int generateRandomInt(int upperRange) {
        Random random = new Random();
        return random.nextInt(upperRange);
    }

    public static String timeToString(Timestamp timestamp, String format) {

        return new SimpleDateFormat(format).format(timestamp);

    }


    public static String makeGetRequest(URL url) {
        Response response = null;
        String responseString = null;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            response = okHttpClient.newCall(request).execute();
            responseString = response.body().string();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return responseString;

    }

}
