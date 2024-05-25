package org.study.view.table;

import org.study.Constants;
import org.study.SProcess;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * STable 을 확장하는 SOutputTable
 * 스케줄링 완료된 데이터를 표시하기 위한 테이블
 */
public class SOutputTable extends STable{

    private static final String[] COLUMNS_SUMMARY = Constants.OUTPUT_TABLE_COLUMNS_SUMMARY;

    private final ArrayList<SProcess> processes;

    /**
     * table 생성
     */
    public SOutputTable(ArrayList<SProcess> processes){
        super(COLUMNS_SUMMARY);
        this.processes = processes;
    }

    /**
     * 테이블 갱신
     */
    public void reRandTable(){
        Object[][] data = new Object[processes.size()][COLUMNS_SUMMARY.length];

        for(int i = 0; i < processes.size(); i++){
            SProcess process = processes.get(i);
            data[i][0] = process.getName();
            data[i][1] = process.getExecutionTime();
            data[i][2] = process.getWaitingTime();
            data[i][3] = process.getTurnaroundTime();
            data[i][4] = process.getResponseTime();
        }

        ((DefaultTableModel) getModel()).setDataVector(data, COLUMNS_SUMMARY);
        setTableColumnMinWidth();
    }
}
