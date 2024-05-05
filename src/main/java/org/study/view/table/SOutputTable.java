package org.study.view.table;

import org.study.SProcess;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * STable 을 확장하는 SOutputTable
 */
public class SOutputTable extends STable{

    private static final String[] COLUMNS_SUMMARY = {"Process", "ExecutionTime", "WaitingTime"};

    private int totalExecutionTime = 0;
    private int averageWaitingTime = 0;

    /**
     * table 생성
     */
    public SOutputTable(){
        super(COLUMNS_SUMMARY);
    }

    public int getTotalExecutionTime() {
        return totalExecutionTime;
    }

    public int getAverageWaitingTime() {
        return averageWaitingTime;
    }

    /**
     * 테이블에 데이터 입력
     * @param list SProcess 객체를 담고있는 리스트
     */
    public void setData(ArrayList<SProcess> list){
        Object[][] data = new Object[list.size()][COLUMNS_SUMMARY.length];

        // 임시로 전체 실행시간과 평균 대기시간 계산하는 로직을 추가한 상태.
        totalExecutionTime = 0;
        averageWaitingTime = 0;
        for(int i = 0; i < list.size(); i++){
            SProcess process = list.get(i);
            data[i][0] = process.getName();
            data[i][1] = process.getExecutionTime();
            data[i][2] = process.getWaitingTime();
            totalExecutionTime += process.getExecutionTime();
            averageWaitingTime += process.getWaitingTime();
        }
        totalExecutionTime /= list.size();
        averageWaitingTime /= list.size();

        ((DefaultTableModel) getModel()).setDataVector(data, COLUMNS_SUMMARY);
        setTableColumnMinWidth();
    }
}
