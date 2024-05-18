package org.study;

/**
 * 프로세스 데이터 클래스
 */
public class SProcess {

    private String name;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int responseTime;
    private int executionTime;
    private int waitingTime;
    private int turnaroundTime;

    public SProcess(String name, int arrivalTime, int burstTime, int priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        responseTime = 0;
        executionTime = 0;
        waitingTime = 0;
        turnaroundTime = 0;
    }

    public SProcess(String name, int arrivalTime, int burstTime, int priority, int responseTime, int executionTime, int waitingTime, int turnAroundTime){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.responseTime = responseTime;
        this.executionTime = executionTime;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnAroundTime;
    }

    public SProcess copy(){
        return new SProcess(
                name,
                arrivalTime,
                burstTime,
                priority,
                responseTime,
                executionTime,
                waitingTime,
                turnaroundTime
        );
    }

    public String getName(){return name;}
    public int getArrivalTime(){return arrivalTime;}
    public int getBurstTime(){return burstTime;}
    public int getPriority(){return priority;}
    public int getResponseTime(){return responseTime;}
    public int getExecutionTime() {return executionTime;}
    public int getWaitingTime(){return waitingTime;}
    public int getTurnaroundTime() {return turnaroundTime;}

    public void setName(String name) {this.name = name;}

    public void setArrivalTime(int arrivalTime) {
        if(arrivalTime >= 0) this.arrivalTime = arrivalTime;
        else throw new InvalidInputException("Arrival time cannot be negative");
    }

    public void setBurstTime(int burstTime) {
        if(burstTime >= 0) this.burstTime = burstTime;
        else throw new InvalidInputException("Burst time cannot be negative");
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setResponseTime(int responseTime){
        if(responseTime >= 0) this.responseTime = responseTime;
        else throw new InvalidInputException("Response time cannot be negative");
    }

    public void setExecutionTime(int executionTime){
        if(executionTime >= 0) this.executionTime = executionTime;
        else throw new InvalidInputException("Execution time cannot be negative");
    }

    public void setWaitingTime(int waitingTime){
        if(waitingTime >= 0) this.waitingTime = waitingTime;
        else throw new InvalidInputException("Waiting time cannot be negative");
    }

    public void setTurnaroundTime(int turnAroundTime) {
        if(turnAroundTime >= 0) this.turnaroundTime = turnAroundTime;
        else throw new InvalidInputException("Turnaround time cannot be negative");
    }

}
