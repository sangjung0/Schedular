package org.study.view.table;

import org.study.SProcess;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * STable을 확장하는 SInputTable
 */
public class SInputTable extends STable{

    private static final String[] COLUMNS_SUMMARY = {"Process", "ArrivalTime", "ExecutionTime", "Priority"};

    /**
     * table 생성
     */
    public SInputTable(){
        super(COLUMNS_SUMMARY);
    }

    /**
     * 테이블에 데이터 입력
     * @param list SProcess 객체를 담고있는 리스트
     */
    public void setData(ArrayList<SProcess> list){
        Object[][] data = new Object[list.size()][COLUMNS_SUMMARY.length];

        for(int i = 0; i < list.size(); i++){
            SProcess process = list.get(i);
            data[i][0] = process.getName();
            data[i][1] = process.getArrivalTime();
            data[i][2] = process.getBurstTime();
            data[i][3] = process.getPriority();
        }

        ((DefaultTableModel) getModel()).setDataVector(data, COLUMNS_SUMMARY);
        setTableColumnMinWidth();
    }
}
