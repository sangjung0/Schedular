package org.study.view.table;

import org.study.Constants;
import org.study.SProcess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * STable을 확장하는 SInputTable
 * 입력된 프로세스를 표의 형태로 출력
 */
public class SInputTable extends STable{

    private static final String[] COLUMNS_SUMMARY = Constants.INPUT_TABLE_COLUMNS_SUMMARY;
    private static final String REMOVE = Constants.TABLE_REMOVE;
    private static final String ADD = Constants.TABLE_ADD;
    private final ArrayList<SProcess> data;

    /**
     * table 생성
     */
    public SInputTable(ArrayList<SProcess> storage){
        super(COLUMNS_SUMMARY);
        setUpPopupMenu();
        data = storage;
    }

    /**
     * 테이블 다시 랜더링
     */
    public void reRandTable(){
        Object[][] data = new Object[this.data.size()][COLUMNS_SUMMARY.length];

        for(int i = 0; i < this.data.size(); i++){
            SProcess process = this.data.get(i);
            data[i][0] = process.getName();
            data[i][1] = process.getArrivalTime();
            data[i][2] = process.getBurstTime();
            data[i][3] = process.getPriority();
        }

        ((DefaultTableModel) getModel()).setDataVector(data, COLUMNS_SUMMARY);
        setTableColumnMinWidth();
    }

    /**
     * 테이블에서 데이터 추가할 수 있게 하는 이벤트 추가
     */
    private void setUpPopupMenu() {
        JPopupMenu tablePopupMenu = new JPopupMenu();
        JPopupMenu headerPopupMenu = new JPopupMenu();

        JMenuItem addItemAtHeader = new JMenuItem(ADD);
        addItemAtHeader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                data.addFirst(new SProcess("", 0, 0,0));
                reRandTable();
            }
        });

        JMenuItem addItem = new JMenuItem(ADD);
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = getSelectedRow();
                data.add(selectedRow == -1 ? 0 : selectedRow+1, new SProcess("", 0, 0,0));
                reRandTable();
            }
        });

        JMenuItem removeItem = new JMenuItem(REMOVE);
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = getSelectedRow();
                if (selectedRow != -1) {
                    data.remove(selectedRow);
                    reRandTable();
                }
            }
        });

        tablePopupMenu.add(addItem);
        tablePopupMenu.add(removeItem);
        headerPopupMenu.add(addItemAtHeader);

        getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    headerPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = rowAtPoint(e.getPoint());
                    setRowSelectionInterval(row, row);

                    tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        getModel().addTableModelListener(e ->{
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (row >= 0 && column >= 0) {
                Object value = getValueAt(row, column);
                SProcess process = data.get(row);
                switch (column) {
                    case 0:
                        process.setName((String) value);
                        break;
                    case 1:
                        process.setArrivalTime(Integer.parseInt(value.toString()));
                        break;
                    case 2:
                        process.setBurstTime(Integer.parseInt(value.toString()));
                        break;
                    case 3:
                        process.setPriority(Integer.parseInt(value.toString()));
                        break;
                }
            }
        });
    }
}
