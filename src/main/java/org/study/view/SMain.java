package org.study.view;

import org.study.Constants;
import org.study.scheduler.*;
import org.study.view.barChart.BarChart;
import org.study.view.button.SAllRun;
import org.study.view.button.SClear;
import org.study.view.button.SOpenFile;
import org.study.view.button.SRun;
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

    private final SInputTable inputTable;
    private final SOutputTable outputTable;
    private final SOpenFile openFile;
    private final SSchedulerSelector comboBox;
    private final SRun run;
    private final SClear clear;
    private final SAllRun allRun;
    private final STotalExecutionTime totalExecutionTime;
    private final SAverageWaitingTime averageWaitingTime;
    private final SContextSwitchCount contextSwitchCount;
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
        contextSwitchCount = new SContextSwitchCount();
        gantt = new Gantt(storage.getGanttData());
        barChart = new BarChart();
        comboBox = new SSchedulerSelector();
        clear = new SClear();
        allRun = new SAllRun();

        init();
        setEvent();
    }

    /**
     * 각 버튼들 이벤트 설정
     */
    private void setEvent(){
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
        run.setCallback(()->run(comboBox.getSelected()));
        allRun.setCallback(()->{
            Iterator<Class<? extends Scheduler>> iter = comboBox.iterator();
            while(iter.hasNext())
                run(iter.next());
        });
        clear.setCallback(barChart::clear);
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

        // 버튼 그룹
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonGroup.add(openFile);
        buttonGroup.add(clear);
        buttonGroup.add(comboBox);
        buttonGroup.add(run);
        buttonGroup.add(allRun);

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
        add(outputGroup, constraints);

        constraints.gridy = 5;
        constraints.weighty = 1;
        add(gantt, constraints);

        constraints.gridy = 6;
        constraints.weighty = 3;
        add(barChart, constraints);

        constraints.gridy = 7;
        constraints.weighty = 0;
        add(buttonGroup, constraints);


        setVisible(true);
    }

    public static void main(String[] args) {
        new SMain();
    }
}