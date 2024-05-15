package org.study.view.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SButton을 확장하는 SRun 버튼
 */
public class SRun extends SButton{

    private static final String NAME = "Run";

    private ActionListener method;

    public SRun(){
        this(()->{});
    }

    /**
     * SRun 입력 될때 callback 함수 실행
     * @param callback 이벤트 callback
     */
    public SRun(Callback callback) {
        super(NAME);
        setCallback(callback);
    }

    /**
     * 이벤트
     * @param callback 이벤트 callback
     */
    public void setCallback(Callback callback){
        if (method != null) removeActionListener(method);
        addActionListener((method = new action(callback)));
    }

    /**
     * callback 인터페이스
     */
    public interface Callback{
        void func();
    }

    /**
     * 이벤트리스너 구현 클래스
     * @param callback 이벤트 callback
     */
    private record action(Callback callback) implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            callback.func();
        }
    }
}
