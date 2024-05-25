package org.study.view.label;

import javax.swing.*;

/**
 * JLabel을 확장한 SLabel
 */
public abstract class SLabel extends SLabelStyle {

    private int count = 0;
    private String name = "";

    public SLabel(String name){
        super();
        this.name = name;
        setTime();
    }

    /**
     * "{name}: {count}" 으로 초기화
     * count 은 기존 count 값.
     */
    public void setTime(){
        setText(name+": "+count);
    }

    /**
     * "{name: {count}" 으로 초기화
     * @param count 정수
     */
    public void setTime(int count){
        this.count = count;
        setTime();
    }

    /**
     * "{name: 0" 으로 초기화
     */
    public void setZero(){
        setTime(0);
    }

}
