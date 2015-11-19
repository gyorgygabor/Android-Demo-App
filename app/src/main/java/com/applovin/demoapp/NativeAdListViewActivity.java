package com.applovin.demoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applovin.demoapp.nativeads.CarouselUINativeAdActivity;
import com.applovin.demoapp.nativeads.RecyclerViewNativeAdsActivity;

public class NativeAdListViewActivity extends BaseActivity {

    private static final int POSITION_SINGLE = 0;
    private static final int POSITION_MULTIPLE = 1;

    private ListItem[] items = {
            new ListItem("Single ad", "Programatically load an ad using our open-source carousel view"),
            new ListItem("Multiple ads", "Simple native ads in a RecyclerView")
    };

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad_list_view);

        listView = (ListView) findViewById(R.id.listView);
        setupListViewFooter();
        ArrayAdapter<ListItem> listAdapter = new ArrayAdapter<ListItem>(this, android.R.layout.simple_list_item_2, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View row = convertView;
                if (row == null) {
                    LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                ListItem item = items[position];

                TextView title = (TextView) row.findViewById(android.R.id.text1);
                title.setText(item.getTitle());
                TextView subtitle = (TextView) row.findViewById(android.R.id.text2);
                subtitle.setText(item.getSubtitle());

                return row;
            }
        };
        listView.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == POSITION_SINGLE) {
                    Intent intent = new Intent(NativeAdListViewActivity.this, CarouselUINativeAdActivity.class);
                    startActivity(intent);
                } else if (position == POSITION_MULTIPLE) {
                    Intent intent = new Intent(NativeAdListViewActivity.this, RecyclerViewNativeAdsActivity.class);
                    startActivity(intent);
                }
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }

    public void setupListViewFooter() {
        TextView footer = new TextView(getApplicationContext());
        footer.setPadding(0, 20, 0, 0);
        footer.setGravity(Gravity.CENTER);
        footer.setTextSize(18);
        footer.setTextColor(Color.RED);
        footer.setText(Html.fromHtml("You must turn <b>ON</b> \" Native Ads\" in your AppLovin dashboard"));

        listView.addFooterView(footer);
        listView.setFooterDividersEnabled(false);
    }
}
