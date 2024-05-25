package org.study.view.label;

import org.study.Constants;

import javax.swing.*;

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
