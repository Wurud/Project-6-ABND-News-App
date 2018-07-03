package com.example.wurudaldibasi.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AppLoader extends AsyncTaskLoader<List<Article>> {

    private String currentUrl;

    public AppLoader(Context context, String orderByUri) {
        super(context);
        this.currentUrl = orderByUri;
    }

    @Override
    public List<Article> loadInBackground() {

        List<Article> articleList = new ArrayList<>();
        try {

            URL url;
            url = new URL(currentUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject root = new JSONObject(builder.toString());
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            int counter = 0;
            while (counter < results.length()) {

                String authorName = " - ";

                if (results.getJSONObject(counter).getString("tags") != "") {

                    JSONArray tags = (JSONArray) results.getJSONObject(counter).get("tags");

                    if (tags.length() > 0) {
                        authorName = tags.getJSONObject(0).getString("webTitle");
                    }
                }

                Article article = new Article(

                        results.getJSONObject(counter).getString("webTitle"),
                        results.getJSONObject(counter).getString("sectionName"),
                        results.getJSONObject(counter).getString("webPublicationDate"),
                        authorName,
                        results.getJSONObject(counter).getString("webUrl")

                );

                articleList.add(article);
                counter++;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return articleList;
    }
}
