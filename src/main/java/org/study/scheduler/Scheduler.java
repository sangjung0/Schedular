package org.study.scheduler;

import org.study.SProcess;

import java.util.Comparator;
import java.util.Iterator;

public abstract class Scheduler {

    protected abstract int getRunTime(SProcess currentProcess);
    protected abstract void addReadyQ(SProcess process);
    protected abstract boolean readyQIsEmpty();
    protected abstract SProcess pollReadyQ();
    protected abstract SProcess peekReadyQ();
    protected abstract Iterator<SProcess> iterableReadyQ();

}
