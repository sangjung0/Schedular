package org.study;

public class SProcess {

    private final String name;
    private final int burstTime;
    private final int priority;
    private final int arrivalTime;
    private int responseTime;
    private int executionTime;
    private int waitingTime;
    private int turnAroundTime;

    public SProcess(String name, int arrivalTime, int burstTime, int priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        executionTime = 0;
        waitingTime = 0;
        responseTime = 0;
        turnAroundTime = 0;
    }

    public SProcess(String name, int arrivalTime, int burstTime, int priority, int executionTime, int waitingTime, int responseTime, int turnAroundTime){
        this.name = name;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.executionTime = executionTime;
        this.waitingTime = waitingTime;
        this.responseTime = responseTime;
        this.turnAroundTime = turnAroundTime;
    }

    public SProcess copy(){
        return new SProcess(
                name,
                burstTime,
                priority,
                arrivalTime,
                executionTime,
                waitingTime,
                responseTime,
                turnAroundTime
        );
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }
    public int getTurnAroundTime() {
        return turnAroundTime;
    }
    public void setResponseTime(int time){responseTime = time;}
    public int getResponseTime(){return responseTime;}
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
