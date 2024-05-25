package org.study.view.button;

import org.study.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JButton을 확장하는 추상클래스
 */
public abstract class SButton extends JButton {

    private ActionListener method;

    /**
     * 버튼 클릭 될때 callback 함수 실행
     * @param callback 이벤트 callback
     */
    public SButton(String name, Callback callback){
        super(name);
        setCallback(callback);

        // style
        setFont(getFont().deriveFont(Constants.BUTTON_FONT_SIZE));
    }

    /**
     * 이벤트
     * @param callback 이벤트 callback
     */
    public void setCallback(Callback callback){
        if (method != null) removeActionListener(method);
        addActionListener((method = new SButton.action(callback)));
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
