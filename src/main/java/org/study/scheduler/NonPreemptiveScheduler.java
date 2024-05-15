package org.study.scheduler;

import org.study.SProcess;

public abstract class NonPreemptiveScheduler extends Scheduler{

    @Override
    protected int getRunTime(SProcess currentProcess) {
        return currentProcess.getBurstTime();
    }
}
