package org.study.view;

import org.study.GanttData;
import org.study.SProcess;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/**
 * ArrayList<SProcess> 를 확장하는 리스트
 */
public class Storage{

    private final ArrayList<SProcess> processes;
    private final ArrayList<SProcess> scheduled;
    private final ArrayList<GanttData> ganttData;

    public Storage(){
        processes = new ArrayList<SProcess>();
        ganttData = new ArrayList<GanttData>();
        scheduled = new ArrayList<SProcess>();
    }

    public ArrayList<SProcess> getProcesses() {return processes;}
    public ArrayList<GanttData> getGanttData() {return ganttData;}
    public ArrayList<SProcess> getScheduled() {return scheduled;}

    public void clearProcesses(){processes.clear();}
    public void clearGanttData(){ganttData.clear();}
    public void clearScheduled(){scheduled.clear();}
    public ArrayList<SProcess> newScheduled(){
        clearScheduled();
        for(SProcess p: processes) scheduled.add(p.copy());
        return scheduled;
    }
    public ArrayList<GanttData> newGanttData(){
        clearGanttData();
        return ganttData;
    }

    /**
     * 파일을 읽어 리스트에 SProcess 객체 추가.
     * 파일의 각 행은 아래와 같아야 함.
     * "{name} {arrivalTime} {executionTime} {priority}\n"
     * @param file 읽을 파일
     */
    public void read(File file) throws IOException{
        ArrayList<SProcess> temp = new ArrayList<SProcess>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        String line;
        while((line = reader.readLine()) != null){
            String[] data = line.split(" ");
            temp.add(new SProcess(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }
        clearProcesses();
        clearGanttData();
        clearScheduled();
        processes.addAll(temp);
    }

    private void makeRandomData(int minBurst, int maxBurst){
        clearProcesses();
        clearGanttData();
        clearScheduled();
        Random random = new Random();
        int count = random.nextInt(20,41);
        for(int i =0; i<count; i++){
            processes.add(new SProcess("p"+i, random.nextInt(1,101), random.nextInt(minBurst,maxBurst), random.nextInt(31)));
        }
        
    }
    public void makeRandomData() {makeRandomData(1, 501);}
    public void makeShortBurstRandomData() {makeRandomData(1, 51);}
    public void makeLongBurstRandomData() {makeRandomData(300, 501);}

    public static int averageResponseTime(ArrayList<SProcess> data){
        int sum = 0;
        for(SProcess p: data){
            sum += p.getResponseTime();
        }
        return sum / data.size();
    }
    public static int averageWaitingTime(ArrayList<SProcess> data){
        int sum = 0;
        for(SProcess p: data){
            sum += p.getWaitingTime();
        }
        return sum / data.size();
    }
    public static int averageTurnaroundTime(ArrayList<SProcess> data){
        int sum = 0;
        for(SProcess p: data){
            sum += p.getTurnaroundTime();
        }
        return sum / data.size();
    }
    public static int totalExecutionTime(ArrayList<SProcess> data){
        int sum = 0;
        for(SProcess p: data){
            sum += p.getExecutionTime();
        }
        return sum;
    }
}
