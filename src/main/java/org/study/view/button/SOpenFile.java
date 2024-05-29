package org.study.view.button;

import org.study.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * SButton 확장하는 OpenFile 버튼
 */
public class SOpenFile extends SButton{

    private static final String NAME = Constants.BUTTON_OPEN_FILE;

    private ActionListener method;

    public SOpenFile(){
        this(null, (file)->{});
    }

    /**
     * SOpenFile 모달 창을 출력하여 파일 입력 받아 callback 함수에 전달
     * @param frame 모달 출력 frame
     * @param callback 이벤트 callback
     */
    public SOpenFile(JFrame frame, Callback callback){
        super(NAME, ()->{});
        setCallback(frame, callback);
    }

    /**
     * 이벤트를 세팅하는 함수. 모달 생성 위치는 지정 안함
     * @param callback 이벤트 callback
     */
    public void setCallback(Callback callback){
        setCallback(null, callback);
    }

    /**
     * 이벤트를 세팅하는 함수.
     * @param frame 모달 출력 frame
     * @param callback 이벤트 callback
     */
    public void setCallback(JFrame frame, Callback callback){
        if (method != null) removeActionListener(method);
        addActionListener((method = new action(frame, callback)));
    }

    /**
     * callback 인터페이스
     */
    public interface Callback{
        void func(File file);
    }

    /**
     * 이벤트리스너 구현 클래스
     * @param frame 모달 출력 frame
     * @param callback 이벤트 callback
     */
    private record action(JFrame frame, Callback callback) implements ActionListener {

        @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
                fileChooser.addChoosableFileFilter(filter);
    
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    callback.func(selectedFile);
                }
            }
        }
}
