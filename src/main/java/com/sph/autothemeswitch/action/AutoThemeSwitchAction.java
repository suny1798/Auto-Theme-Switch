package com.sph.autothemeswitch.action;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sph.autothemeswitch.ui.AutoThemeSwitchDialog;
import org.jetbrains.annotations.NotNull;

public class AutoThemeSwitchAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AutoThemeSwitchDialog dialog = new AutoThemeSwitchDialog(e.getProject());
        dialog.show();
    }
}