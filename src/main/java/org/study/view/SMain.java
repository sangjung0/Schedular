package org.study.view;

import org.study.scheduler.*;
import org.study.view.barChart.BarChart;
import org.study.view.button.SClear;
import org.study.view.button.SOpenFile;
import org.study.view.button.SRun;
import org.study.view.label.SAverageWaitingTime;
import org.study.view.label.STotalExecutionTime;
import org.study.view.table.SInputTable;
import org.study.view.table.SOutputTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * GUI 메인 클래스
 */
public class SMain extends JFrame {

    private static final int X = 1000;
    private static final int Y = 1000;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 900;
    private static final int FRAME_PADDING = 20;

    private final SInputTable inputTable;
    private final SOutputTable outputTable;
    private final SOpenFile openFile;
    private final SSchedulerSelector comboBox;
    private final SRun run;
    private final SClear clear;
    private final STotalExecutionTime totalExecutionTime;
    private final SAverageWaitingTime averageWaitingTime;
    private final Gantt gantt;
    private final BarChart barChart;

    private final Storage storage;


    public SMain(){
        storage = new Storage();
        inputTable = new SInputTable(storage.getProcesses());
        outputTable = new SOutputTable(storage.getScheduled());
        openFile = new SOpenFile();
        run = new SRun();
        totalExecutionTime = new STotalExecutionTime();
        averageWaitingTime = new SAverageWaitingTime();
        gantt = new Gantt(storage.getGanttData());
        barChart = new BarChart();
        comboBox = new SSchedulerSelector();
        clear = new SClear();

        init();
        setEvent();
    }

    /**
     * 각 버튼들 이벤트 설정
     */
    private void setEvent(){
        openFile.setCallback(this, file -> {
            storage.read(file); //에러 처리 필요
            inputTable.reRandTable();
        });
        run.setCallback(()->{
            try{
                if(storage.getProcesses().isEmpty()) return;
                (new Processor(comboBox.getSelected())).start(storage.newScheduled(), storage.newGanttData());
                outputTable.reRandTable();
                totalExecutionTime.setTime(Storage.totalExecutionTime(storage.getScheduled()));
                averageWaitingTime.setTime(Storage.averageWaitingTime(storage.getScheduled()));
                gantt.reRandGantt();
                barChart.updateDataset(
                        comboBox.getSelectedName(),
                        Storage.averageWaitingTime(storage.getScheduled()),
                        Storage.averageTurnaroundTime(storage.getScheduled()),
                        Storage.averageResponseTime(storage.getScheduled())
                );
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        clear.setCallback(barChart::clear);
    }

    /**
     * 창의 기본 구성요소 세팅
     */
    private void init(){
        setTitle("Scheduler");

        // 초기 창 위치 및 크기
        setLocation(X,Y);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH,HEIGHT);
        //setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 패딩
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(FRAME_PADDING, FRAME_PADDING, FRAME_PADDING, FRAME_PADDING));

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // 입력
        JLabel input = new JLabel("input");
        JScrollPane inputPane = new JScrollPane(inputTable);

        // 출력
        JLabel output = new JLabel("output");
        JScrollPane outputPane = new JScrollPane(outputTable);

        // 버튼 그룹
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonGroup.add(openFile);
        buttonGroup.add(clear);
        buttonGroup.add(comboBox);
        buttonGroup.add(run);

        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        add(input, constraints);

        constraints.gridy = 1;
        constraints.weighty = 1;
        add(inputPane, constraints);

        constraints.gridy = 2;
        constraints.weighty = 0;
        add(output, constraints);

        constraints.gridy = 3;
        constraints.weighty = 1;
        add(outputPane, constraints);

        constraints.gridy = 4;
        constraints.weighty = 0;
        add(totalExecutionTime, constraints);

        constraints.gridy = 5;
        add(averageWaitingTime, constraints);

        constraints.gridy = 6;
        constraints.weighty = 1;
        add(gantt, constraints);

        constraints.gridy = 7;
        constraints.weighty = 3;
        add(barChart, constraints);

        constraints.gridy = 8;
        constraints.weighty = 0;
        add(buttonGroup, constraints);


        setVisible(true);
    }

    public static void main(String[] args) {
        new SMain();
    }
}