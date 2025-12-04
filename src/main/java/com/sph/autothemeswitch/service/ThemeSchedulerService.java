package com.sph.autothemeswitch.service;

import com.intellij.ide.ui.LafManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.sph.autothemeswitch.model.ThemeSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

@Service
@State(name = "AutoThemeSwitchSettings", storages = @Storage("autoThemeSwitch.xml"))
public final class ThemeSchedulerService implements PersistentStateComponent<ThemeSettings> {
    private ThemeSettings settings = new ThemeSettings();
    private Timer timer;

    public static ThemeSchedulerService getInstance() {
        return ApplicationManager.getApplication().getService(ThemeSchedulerService.class);
    }

    @Override
    public @Nullable ThemeSettings getState() {
        return settings;
    }

    @Override
    public void loadState(@NotNull ThemeSettings state) {
        this.settings = state;
        if (settings.isEnabled()) {
            startScheduler();
        }
    }

    public ThemeSettings getSettings() {
        return settings;
    }

    public void updateSettings(ThemeSettings newSettings) {
        this.settings = newSettings;
    }

    public void startScheduler() {
        stopScheduler();
        timer = new Timer(true);

        // 每分钟检查一次
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAndSwitchTheme();
            }
        }, 0, 60000);
    }

    public void stopScheduler() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

// service/ThemeSchedulerService.java - 只需修改 checkAndSwitchTheme() 方法

    private void checkAndSwitchTheme() {
        if (!settings.isEnabled()) return;

        LocalTime now = LocalTime.now();
        int currentMinutes = now.getHour() * 60 + now.getMinute();

        // 计算两个设定时间的分钟数
        int time1Minutes = settings.getHour1() * 60 + settings.getMinute1();
        int time2Minutes = settings.getHour2() * 60 + settings.getMinute2();

        // 判断当前应该使用哪个主题（时间越大优先级越高）
        String targetTheme = null;

        if (time1Minutes > time2Minutes) {
            // 时间1更大，优先级更高
            if (currentMinutes >= time1Minutes) {
                targetTheme = settings.getTheme1();
            } else if (currentMinutes >= time2Minutes) {
                targetTheme = settings.getTheme2();
            }
        } else {
            // 时间2更大，优先级更高
            if (currentMinutes >= time2Minutes) {
                targetTheme = settings.getTheme2();
            } else if (currentMinutes >= time1Minutes) {
                targetTheme = settings.getTheme1();
            }
        }

        // 切换到目标主题（如果需要切换）
        if (targetTheme != null && !targetTheme.isEmpty()) {
            String currentTheme = getCurrentThemeName();
            if (!targetTheme.equals(currentTheme)) {
                switchToTheme(targetTheme);
            }
        }
    }

    // 添加一个辅助方法获取当前主题名称
    private String getCurrentThemeName() {
        LafManager lafManager = LafManager.getInstance();
        UIManager.LookAndFeelInfo currentLaf = lafManager.getCurrentLookAndFeel();
        return currentLaf != null ? currentLaf.getName() : null;
    }

    public void switchToTheme(String themeName) {
        if (themeName == null || themeName.isEmpty()) return;

        ApplicationManager.getApplication().invokeLater(() -> {
            LafManager lafManager = LafManager.getInstance();
            UIManager.LookAndFeelInfo targetLaf = null;

            for (UIManager.LookAndFeelInfo laf : lafManager.getInstalledLookAndFeels()) {
                if (laf.getName().equals(themeName)) {
                    targetLaf = laf;
                    break;
                }
            }

            if (targetLaf != null) {
                lafManager.setCurrentLookAndFeel(targetLaf);
                lafManager.updateUI();
            }
        });
    }
}