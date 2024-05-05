package org.study.view.label;

/**
 * SLabel 을 확장한 STotalExecutionTime
 */
public class STotalExecutionTime extends SLabel{
    private static final String NAME = "total execution time";

    private int time = 0;

    /**
     * 버튼 초기화
     */
    public STotalExecutionTime() {
        super();
        setTime();
    }

    /**
     * "total execution time: {time}" 으로 초기화
     * time 은 기존 time 값.
     */
    public void setTime(){
        setText(NAME+": "+time);
    }

    /**
     * "total execution time: {time}" 으로 초기화
     * @param time 시간
     */
    public void setTime(int time){
        this.time = time;
        setTime();
    }
    /**
     * "total execution time: 0" 으로 초기화
     */
    public void setZero(){
        setTime(0);
    }
}
