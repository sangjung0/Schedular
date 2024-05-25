package org.study.view.label;

import org.study.Constants;

import javax.swing.*;

/**
 * gui에 쓰일 버튼들 디자인하는 추상 클래스
 */
public abstract class SLabelStyle extends JLabel {
    public SLabelStyle(String name){
        super(name);

        // 스타일
        setFont(getFont().deriveFont(Constants.LABEL_FONT_SIZE));
    }

    public SLabelStyle(){
        this("");
    }
}
