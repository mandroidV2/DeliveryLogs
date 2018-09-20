package com.app.deliverieslogs.presentation.delieverylist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.deliverieslogs.R;
import com.app.deliverieslogs.common.Constants;
import com.app.deliverieslogs.common.Utils;
import com.app.deliverieslogs.models.DeliveryItem;
import com.app.deliverieslogs.presentation.BaseActivity;
import com.app.deliverieslogs.presentation.deliverydetail.DeliveryDetailsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/** Activity is used to create the list of deliveries
 * @author mandroid_v2.0 */
public class DeliveryListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView itemList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_delivey_list);

        // find the widgets
        itemList = findViewById(R.id.itemList);
        progressBar = findViewById(R.id.progressBar);

        // start the asyntask to insert the data into database table
        new AsyncTask<Void, Void, List<DeliveryItem>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                itemList.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected List<DeliveryItem> doInBackground(final Void ... params) {
                List<DeliveryItem> deliveryItems  = null;
                try {
                    if (Utils.isNetworkAvailable(DeliveryListActivity.this)) {
                        //------------------>>
                        HttpGet httppost = new HttpGet(Constants.URL_GET_DELIVERIES);
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpResponse response = httpclient.execute(httppost);
                        final int status = response.getStatusLine().getStatusCode();
                        if (status == 200) {
                            HttpEntity entity = response.getEntity();
                            String json = EntityUtils.toString(entity);
                            // save json in shared pref
                            getSharedPreferences().edit().putString(Constants.PREF_DELIVERY_JSON, json).apply();
                            // get the delivery items
                            deliveryItems = getListFromJson(json);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DeliveryListActivity.this, "Api Fail : " + status, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        String json = getSharedPreferences().getString(Constants.PREF_DELIVERY_JSON, "");
                        // get the delivery items
                        deliveryItems = getListFromJson(json);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return deliveryItems;
            }

            @Override
            protected void onPostExecute(final List<DeliveryItem> result) {
                itemList.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                onReceivedDeliveryItems(result);
            }
        }.execute();
    }

    /** Methos is used to get the list from json
     * @param json
     * @return */
    private List<DeliveryItem> getListFromJson(String json) {
        Gson gson = new GsonBuilder().create();
        Type typeListCategories = new TypeToken<List<DeliveryItem>>(){}.getType();
        return gson.fromJson(json, typeListCategories);
    }

    /** Method is used to set the adapter to the list
     * @param deliveryItems */
    public void onReceivedDeliveryItems(List<DeliveryItem> deliveryItems) {
        if (deliveryItems == null)
            return;
        DeliveryListAdapter adapter = new DeliveryListAdapter(activity, deliveryItems);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeliveryItem deliveryItem = (DeliveryItem) parent.getAdapter().getItem(position);
        Intent intent = new Intent(activity, DeliveryDetailsActivity.class);
        intent.putExtra(Constants.DELIVERY_LOG_OBJ_KEY, deliveryItem);
        startActivity(intent);
    }
}