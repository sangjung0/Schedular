package org.study.view;

import org.study.Constants;
import org.study.scheduler.*;
import org.study.view.barChart.BarChart;
import org.study.view.button.*;
import org.study.view.label.*;
import org.study.view.table.SInputTable;
import org.study.view.table.SOutputTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * GUI 메인 클래스
 */
public class SMain extends JFrame {

    private static final int X = Constants.WINDOW_X;
    private static final int Y = Constants.WINDOW_Y;
    private static final int WIDTH = Constants.WINDOW_WIDTH;
    private static final int HEIGHT = Constants.WINDOW_HEIGHT;
    private static final int FRAME_PADDING = Constants.FRAME_PADDING;
    private static final String TITLE = Constants.TITLE;

    private static final int THREE_LABEL_H_GAP = Constants.THREE_LABEL_H_GAP;

    // 버튼 이름
    private static final String BUTTON_RANDOM = Constants.BUTTON_RANDOM;
    private static final String BUTTON_ALL_RUN = Constants.BUTTON_ALL_RUN;
    private static final String BUTTON_CHART_CLEAR = Constants.BUTTON_CHART_CLEAR;
    private static final String BUTTON_RUN = Constants.BUTTON_RUN;
    private static final String BUTTON_INPUT_CLEAR = Constants.BUTTON_INPUT_CLEAR;
    private static final String BUTTON_OUTPUT_CLEAR = Constants.BUTTON_OUTPUT_CLEAR;
    private static final String BUTTON_LONG_BURST_RANDOM = Constants.BUTTON_LONG_BURST_RANDOM;
    private static final String BUTTON_SHORT_BURST_RANDOM = Constants.BUTTON_SHORT_BURST_RANDOM;;
    private static final String BUTTON_CLEAR = Constants.BUTTON_CLEAR;

    private final Storage storage;
    private final BarChart barChart;
    private final Gantt gantt;

    private final SSchedulerSelector comboBox;

    private final SInputTable inputTable;
    private final SOutputTable outputTable;

    private final STotalExecutionTime totalExecutionTime;
    private final SAverageWaitingTime averageWaitingTime;
    private final SContextSwitchCount contextSwitchCount;

    private final SOpenFile openFile;
    private final SButton run;
    private final SButton chartClear;
    private final SButton allRun;
    private final SButton random;
    private final SButton inputClear;
    private final SButton outputClear;
    private final SButton longBurstRandom;
    private final SButton shortBurstRandom;
    private final SButton clear;



    public SMain(){
        storage = new Storage();
        barChart = new BarChart();
        gantt = new Gantt(storage.getGanttData());

        comboBox = new SSchedulerSelector();

        // 테이블
        inputTable = new SInputTable(storage.getProcesses());
        outputTable = new SOutputTable(storage.getScheduled());

        // 라벨
        totalExecutionTime = new STotalExecutionTime();
        averageWaitingTime = new SAverageWaitingTime();
        contextSwitchCount = new SContextSwitchCount();

        // 버튼
        openFile = new SOpenFile();
        run = new SButton(BUTTON_RUN);
        chartClear = new SButton(BUTTON_CHART_CLEAR);
        allRun = new SButton(BUTTON_ALL_RUN);
        random = new SButton(BUTTON_RANDOM);
        inputClear = new SButton(BUTTON_INPUT_CLEAR);
        outputClear = new SButton(BUTTON_OUTPUT_CLEAR);
        longBurstRandom = new SButton(BUTTON_LONG_BURST_RANDOM);
        shortBurstRandom = new SButton(BUTTON_SHORT_BURST_RANDOM);
        clear = new SButton(BUTTON_CLEAR);

        init();
        setEvent();
    }

    /**
     * 각 버튼들 이벤트 설정
     */
    private void setEvent(){
        // input
        openFile.setCallback(this, file -> {
            try{
                storage.read(file);
                inputTable.reRandTable();
                barChart.clear();
                outputTable.reRandTable();
                gantt.reRandGantt();
            }catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(this, Constants.FILE_DOES_NOT_EXIST, Constants.ERROR_MESSAGE_BOX_TITLE, JOptionPane.ERROR_MESSAGE);
            }catch (Exception e){
                JOptionPane.showMessageDialog(this, Constants.FILE_CONTENT_FORMAT_IS_INCORRECT, Constants.ERROR_MESSAGE_BOX_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        });
        random.setCallback(()->{
            storage.makeRandomData();
            inputTable.reRandTable();
            barChart.clear();
            outputTable.reRandTable();
            gantt.reRandGantt();
        });
        shortBurstRandom.setCallback(()->{
            storage.makeShortBurstRandomData();
            inputTable.reRandTable();
            barChart.clear();
            outputTable.reRandTable();
            gantt.reRandGantt();
        });
        longBurstRandom.setCallback(()->{
            storage.makeLongBurstRandomData();
            inputTable.reRandTable();
            barChart.clear();
            outputTable.reRandTable();
            gantt.reRandGantt();
        });

        // run
        run.setCallback(()->run(comboBox.getSelected()));
        allRun.setCallback(()->{
            Iterator<Class<? extends Scheduler>> iter = comboBox.iterator();
            while(iter.hasNext())
                run(iter.next());
        });

        //clear
        clear.setCallback(()->{
            storage.clearProcesses();
            storage.clearGanttData();
            storage.clearScheduled();
            inputTable.reRandTable();
            outputTable.reRandTable();
            gantt.reRandGantt();
            barChart.clear();
        });
        inputClear.setCallback(()->{
            storage.clearProcesses();
            inputTable.reRandTable();
        });
        outputClear.setCallback(()->{
            storage.clearScheduled();
            outputTable.reRandTable();
        });
        chartClear.setCallback(barChart::clear);
    }

    /**
     * 스케줄링 실행 함수
     * @param scheduler 스케줄러 클래스
     */
     private void run(Class<? extends Scheduler> scheduler){
         try{
             if(storage.getProcesses().isEmpty()) return;
             contextSwitchCount.setTime((new Processor(scheduler)).start(storage.newScheduled(), storage.newGanttData()));
             outputTable.reRandTable();
             totalExecutionTime.setTime(Storage.totalExecutionTime(storage.getScheduled()));
             averageWaitingTime.setTime(Storage.averageWaitingTime(storage.getScheduled()));
             gantt.reRandGantt();
             barChart.updateDataset(
                     comboBox.getSchedulerName(scheduler),
                     Storage.averageWaitingTime(storage.getScheduled()),
                     Storage.averageTurnaroundTime(storage.getScheduled()),
                     Storage.averageResponseTime(storage.getScheduled())
             );
         }catch (Exception e){
             JOptionPane.showMessageDialog(this, Constants.RUN_ERROR, Constants.ERROR_MESSAGE_BOX_TITLE, JOptionPane.ERROR_MESSAGE);
         }
     }

    /**
     * 창의 기본 구성요소 세팅
     */
    private void init(){
        setTitle(TITLE);

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
        JLabel input = new SInput();
        JScrollPane inputPane = new JScrollPane(inputTable);

        // 라벨 출력 그룹
        JPanel outputGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, THREE_LABEL_H_GAP, 5));
        outputGroup.add(totalExecutionTime);
        outputGroup.add(averageWaitingTime);
        outputGroup.add(contextSwitchCount);

        // 출력
        JLabel output = new SOutput();
        JScrollPane outputPane = new JScrollPane(outputTable);

        // 입력 버튼 그룹
        JPanel inputButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputButtonGroup.add(openFile);
        inputButtonGroup.add(random);
        inputButtonGroup.add(longBurstRandom);
        inputButtonGroup.add(shortBurstRandom);

        JPanel runButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        runButtonGroup.add(comboBox);
        runButtonGroup.add(run);
        runButtonGroup.add(allRun);

        JPanel clearButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clearButtonGroup.add(inputClear);
        clearButtonGroup.add(outputClear);
        clearButtonGroup.add(chartClear);
        clearButtonGroup.add(clear);

        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        constraints.gridy = 0;
        add(input, constraints);

        constraints.gridy++;
        constraints.weighty = 1;
        add(inputPane, constraints);

        constraints.gridy++;
        constraints.weighty = 0;
        add(output, constraints);

        constraints.gridy++;
        constraints.weighty = 1;
        add(outputPane, constraints);

        constraints.gridy++;
        constraints.weighty = 0;
        add(outputGroup, constraints);

        constraints.gridy++;
        constraints.weighty = 1;
        add(gantt, constraints);

        constraints.gridy++;
        constraints.weighty = 3;
        add(barChart, constraints);

        constraints.gridy++;
        constraints.weighty = 0;
        add(inputButtonGroup, constraints);

        constraints.gridy++;
        add(runButtonGroup, constraints);

        constraints.gridy++;
        add(clearButtonGroup, constraints);

        setVisible(true);
    }
}