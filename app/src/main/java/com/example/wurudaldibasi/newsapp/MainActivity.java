package com.example.wurudaldibasi.newsapp;


import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {
    private static String requestUrl = "http://content.guardianapis.com/search?";
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ListAdapter(this, new ArrayList<Article>());
        recyclerView.setAdapter(listAdapter);
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo flag = connectivityManager.getActiveNetworkInfo();

        if (flag == null) {

            Toast.makeText(this, R.string.connection_msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderByQuery = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(requestUrl);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "67eb119e-20b9-4000-aeac-2941175f0e98");
        uriBuilder.appendQueryParameter("sectionId", "technology");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("order-by", orderByQuery);
        uriBuilder.appendQueryParameter("q", "technology");

        return new AppLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        listAdapter = new ListAdapter(this, data);
        emptyView = findViewById(R.id.noData);

        if (data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
