package org.study.scheduler;

import org.study.SProcess;

public class GradualIncreaseRoundRobin extends RoundRobin{

    private static final int TIME_QUANTUM = 8;
    private static final int INCREASE_VALUE = 1;

    public GradualIncreaseRoundRobin() {super(TIME_QUANTUM);}

    @Override
    public int getRunTime(SProcess currentProcess) {
        if (!(currentProcess instanceof GProcess)) return super.getRunTime(currentProcess);
        int remainTime = currentProcess.getBurstTime() - currentProcess.getExecutionTime();
        int timeQuantum = getTimeQuantum() + 31 - Integer.numberOfLeadingZeros(((GProcess)currentProcess).getRunCount());
        return timeQuantum <= remainTime ? timeQuantum : remainTime;
    }

    @Override
    public void addReadyQ(SProcess process) {
        if (process instanceof GProcess gProcess) {
            gProcess.setRunCount(gProcess.getRunCount() + INCREASE_VALUE);
            super.addReadyQ(process);
        }
        else super.addReadyQ(new GProcess(process));
    }

    private static class GProcess extends SProcess {

        private int runCount;
        private final SProcess process;

        public GProcess(SProcess process) {
            super(process.getName(), process.getArrivalTime(), process.getBurstTime(), process.getPriority());
            this.process = process;
            runCount = 1;
        }

        @Override
        public String getName(){return process.getName();}
        @Override
        public int getArrivalTime(){return process.getArrivalTime();}
        @Override
        public int getBurstTime(){return process.getBurstTime();}
        @Override
        public int getPriority(){return process.getPriority();}
        @Override
        public int getResponseTime(){return process.getResponseTime();}
        @Override
        public int getExecutionTime(){return process.getExecutionTime();}
        @Override
        public int getWaitingTime(){return process.getWaitingTime();}
        @Override
        public int getTurnaroundTime(){return process.getTurnaroundTime();}
        @Override
        public GProcess copy(){return new GProcess(process.copy());}

        @Override
        public void setName(String name){process.setName(name);}
        @Override
        public void setArrivalTime(int arrivalTime){process.setArrivalTime(arrivalTime);}
        @Override
        public void setBurstTime(int burstTime){process.setBurstTime(burstTime);}
        @Override
        public void setPriority(int priority){process.setPriority(priority);}
        @Override
        public void setResponseTime(int responseTime){process.setResponseTime(responseTime);}
        @Override
        public void setExecutionTime(int executionTime){process.setExecutionTime(executionTime);}
        @Override
        public void setWaitingTime(int waitingTime){process.setWaitingTime(waitingTime);}
        @Override
        public void setTurnaroundTime(int turnaroundTime){process.setTurnaroundTime(turnaroundTime);}


        public int getRunCount() {return runCount;}
        public void setRunCount(int runCount) {this.runCount = runCount;}
    }
}
