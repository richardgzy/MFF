package com.example.richardssurface.mff;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.richardssurface.mff.Utilities.RestDataReceiver;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;

/**
 * Created by Richard's Surface on 5/3/2017.
 */

public class ReportFragment extends Fragment {
    private View vMain;
    private List<PieEntry> unitAndFrequency;
    private PieChart mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.report_fragment, container, false);
        mChart = (PieChart) vMain.findViewById(R.id.pie_chart);

        new AsyncTask<Void, Void, List<PieEntry>>() {
            @Override
            protected List<PieEntry> doInBackground(Void... params) {
                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();
                return RestDataReceiver.getPopularUnits();
            }

            @Override
            protected void onPostExecute(List<PieEntry> uaf) {
                if(android.os.Debug.isDebuggerConnected())
                    android.os.Debug.waitForDebugger();
                unitAndFrequency = uaf;

                mChart = (PieChart) vMain.findViewById(R.id.pie_chart);
                mChart.getDescription().setEnabled(false);

                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

                mChart.setCenterTextTypeface(tf);
                mChart.setCenterText(generateCenterText());
                mChart.setCenterTextSize(10f);
                mChart.setCenterTextTypeface(tf);

                // radius of the center hole in percent of maximum radius
                mChart.setHoleRadius(45f);
                mChart.setTransparentCircleRadius(50f);

                Legend l = mChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setDrawInside(false);

                mChart.setData(generatePieData());
            }
        }.execute();

        return vMain;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Units and frequency");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    protected PieData generatePieData() {

        PieDataSet ds1 = new PieDataSet(unitAndFrequency, "Units and frequency");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);

        return d;
    }
}
