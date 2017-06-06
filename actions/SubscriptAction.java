package com.javarush.task.task32.task3209.actions;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;
import javax.swing.text.StyleConstants;

/**
 * Created by amalakhov on 06.06.2017.
 */
public class SubscriptAction extends StyledEditorKit.StyledTextAction {


    public SubscriptAction() {
        super(StyleConstants.Subscript.toString());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JEditorPane editor=getEditor(e);
        if (editor!=null){
            MutableAttributeSet mutableAttributeSet = getStyledEditorKit(editor).getInputAttributes();
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            StyleConstants.setSubscript(simpleAttributeSet, !StyleConstants.isSubscript(mutableAttributeSet));
            setCharacterAttributes(editor, simpleAttributeSet, false);
        }

    }
}
