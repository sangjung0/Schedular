package org.study.view.barChart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.study.Constants;

import java.awt.*;

public class BarChart extends ChartPanel {

    private final static String CHART_NAME= Constants.BAR_CHART_NAME;
    private final static String X_AXIS = Constants.BAR_CHART_X_AXIS;
    private final static String Y_AXIS = Constants.BAR_CHART_Y_AXIS;
    private final static String WAITING_TIME = Constants.BAR_CHART_WAITING_TIME;
    private final static String TURNAROUND_TIME =Constants.BAR_CHART_TURNAROUND_TIME;
    private final static String RESPONSE_TIME = Constants.BAR_CHART_RESPONSE_TIME;


    private final DefaultCategoryDataset dataset;

    public BarChart() {
        super(null);
        dataset = new DefaultCategoryDataset();
        JFreeChart chart = createChart();
        customizeChart(chart);
        setChart(chart);
    }

    private JFreeChart createChart() {
        return ChartFactory.createBarChart(
                CHART_NAME,
                X_AXIS,
                Y_AXIS,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
    }

    private void customizeChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(Color.WHITE);

        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setDrawBarOutline(false);

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);

        ItemLabelPosition position = new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER
        );
        renderer.setDefaultPositiveItemLabelPosition(position);
    }

    public void updateDataset(String schedulerName, int averageWaitingTime, int averageTurnaroundTime, int averageResponseTime){
        dataset.setValue(averageWaitingTime, String.valueOf(schedulerName), WAITING_TIME);
        dataset.setValue(averageTurnaroundTime, String.valueOf(schedulerName), TURNAROUND_TIME);
        dataset.setValue(averageResponseTime, String.valueOf(schedulerName), RESPONSE_TIME);
    }

    public void clear(){
        dataset.clear();
    }
}
