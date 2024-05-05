package org.study.view.label;

/**
 * SLabel을 확장한 SAverageWaitingTime
 */
public class SAverageWaitingTime extends SLabel{
    private static final String NAME = "average waiting time";

    private int time = 0;

    /**
     * 버튼 초기화
     */
    public SAverageWaitingTime() {
        super();
        setTime();
    }

    /**
     * "average waiting time: {time}" 으로 초기화
     * time 은 기존 time 값.
     */
    public void setTime(){
        setText(NAME+": "+time);
    }

    /**
     * "average waiting time: {time}" 으로 초기화
     * @param time 시간
     */
    public void setTime(int time){
        this.time = time;
        setTime();
    }

    /**
     * "average waiting time: 0" 으로 초기화
     */
    public void setZero(){
        setTime(0);
    }
}
