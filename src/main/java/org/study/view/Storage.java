package org.study.view;

import org.study.SProcess;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * ArrayList<SProcess> 를 확장하는 리스트
 */
public class Storage extends ArrayList<SProcess>{
    public Storage(){
        super();
    }

    /**
     * 파일을 읽어 리스트에 SProcess 객체 추가.
     * 파일의 각 행은 아래와 같아야 함.
     * "{name} {number} {arrivalTime} {executionTime} {priority}\n"
     * @param file 읽을 파일
     * @return 오류 없으면 false
     */
    public boolean read(File file) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split(" ");
                add(new SProcess(data[0] + data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }
            return false;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
