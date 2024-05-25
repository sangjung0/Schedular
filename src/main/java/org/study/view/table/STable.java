package org.study.view.table;

import org.study.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * JTable을 확장한 테이블 클래스
 */
public abstract class STable extends JTable {

    private static final int TABLE_COLUMN_MIN_WIDTH = 140;
    private static final float FONT_SIZE = Constants.TABLE_FONT_SIZE;
    private static final int TABLE_HEIGHT = Constants.TABLE_HEIGHT;

    /**
     * 컬럼 제목을 받아 테이블 생성
     * @param columns 컬럼 제목
     */
    protected STable(String[] columns){
        super(new DefaultTableModel(columns, 0));
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setTableColumnMinWidth();

        // style
        setFont(getFont().deriveFont(FONT_SIZE));
        setRowHeight(TABLE_HEIGHT);
        getTableHeader().setFont(getTableHeader().getFont().deriveFont(FONT_SIZE));
    }

    /**
     * 각 컬럼의 최소 너비 지정
     */
    protected void setTableColumnMinWidth(){
        for (int i = 0; i < columnModel.getColumnCount(); i++){
            columnModel.getColumn(i).setMinWidth(TABLE_COLUMN_MIN_WIDTH);
        }
    }
}
