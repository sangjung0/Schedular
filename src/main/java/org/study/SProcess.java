package org.study;

public class SProcess {

    private final String name;
    private final int burstTime;
    private final int priority;
    private final int arrivalTime;
    private int executionTime = 0;
    private int waitingTime = 0;

    public SProcess(String name, int arrivalTime, int burstTime, int priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(int value){
        executionTime = value;
    }
    public int getWaitingTime(){
        return waitingTime;
    }
    public void setWaitingTime(int value){
        waitingTime = value;
    }
    public int getBurstTime(){
        return burstTime;
    }
    public String getName(){
        return name;
    }
    public int getPriority(){
        return priority;
    }

}
