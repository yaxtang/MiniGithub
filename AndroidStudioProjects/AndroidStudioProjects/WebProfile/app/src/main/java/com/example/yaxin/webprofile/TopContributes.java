package com.example.yaxin.webprofile;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.black;

/**
 * Created by Yaxin on 11/6/2017.
 */

public class TopContributes extends Fragment {
    View myView;
    public ProgressDialog pDialog;
    public String url = "https://api.github.com/repos/thedaviddias/Front-End-Checklist/commits";
    public String TAG = com.example.yaxin.webprofile.TopContributes.class.getSimpleName();
    int[] times;
    public List<Entry> entries;
    public LineDataSet dataSet;
    public LineData lineData;
    public LineChart chart;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.top_comments,container, false);
        chart = (LineChart) myView.findViewById(R.id.graph);
        times = new int[31];
        for(int i = 0; i < 31;i++){
            times[i] = 0;
        }
        new com.example.yaxin.webprofile.TopContributes.GetData().execute();
        return myView;
    }
    // "https://api.github.com/repos/greyform/Chess/comments
    // top comments
    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray projects = new JSONArray(jsonStr);
                    // looping through All projects
                    int length = projects.length();
                    //first commit
                    for (int i = 0; i < length; i++) {
                        JSONObject commits = projects.getJSONObject(i); // commit
                        JSONObject commit = commits.getJSONObject("commit"); // commit
                        JSONObject author = commit.getJSONObject("author"); // commit
                        String create = author.getString("date"); // commit
                        String check = create.substring(0,7);
                        if (check.equals("2017-11")){
                            int date_ = Integer.parseInt(create.substring(8,10)); ;
                            times[date_-1] = times[date_-1]+1;
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            entries = new ArrayList<Entry>();

            for (int i = 0; i < 31; i++) {
                // turn your data into Entry objects
                Log.e(TAG,"times:"+times[i]);
                entries.add(new Entry(i+1, times[i]));
            }
            dataSet = new LineDataSet(entries, "contribues"); // add entries to dataset
            dataSet.setColor(black);
            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            dataSet.setColor(Color.rgb(45, 229, 187));
            dataSet.setDrawCircles(false);
            dataSet.setLineWidth(4f);
            dataSet.setCircleRadius(5f);
            dataSet.setFillAlpha(150);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(Color.rgb(45, 229, 187));
            dataSet.setHighLightColor(Color.rgb(45, 229, 187));
            dataSet.setDrawCircleHole(false);
            dataSet.setValueTextColor(black); // styling, ...
            lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.setScaleXEnabled(true);
            chart.setScaleYEnabled(true);
            chart.setBackgroundColor(Color.rgb(214, 242, 235));
            chart.getDescription().setEnabled(true);
            // enable touch gestures
            chart.setTouchEnabled(true);
            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(false);
            chart.setDrawGridBackground(false);
            chart.setMaxHighlightDistance(300);

            XAxis x = chart.getXAxis();
            x.setEnabled(true);
            YAxis y = chart.getAxisLeft();
            y.setTextColor(Color.BLACK);
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            y.setDrawGridLines(false);
            y.setAxisLineColor(Color.BLACK);
            x.setGranularity(1f); // restrict interval to 1 (minimum)
            chart.getAxisRight().setEnabled(false);
            chart.getLegend().setEnabled(false);
            chart.invalidate(); // refresh


        }

    }

}

