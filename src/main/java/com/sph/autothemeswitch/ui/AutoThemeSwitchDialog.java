package com.sph.autothemeswitch.ui;


import com.intellij.ide.ui.LafManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.sph.autothemeswitch.model.ThemeSettings;
import com.sph.autothemeswitch.service.ThemeSchedulerService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AutoThemeSwitchDialog extends DialogWrapper {
    private JBCheckBox enableCheckBox;
    private JComboBox<String> theme1ComboBox;
    private JComboBox<String> theme2ComboBox;
    private JSpinner hour1Spinner;
    private JSpinner minute1Spinner;
    private JSpinner hour2Spinner;
    private JSpinner minute2Spinner;

    // 立即切换相关组件
    private JComboBox<String> instantThemeComboBox;
    private JButton switchNowButton;

    private ThemeSchedulerService schedulerService;

    public AutoThemeSwitchDialog(@Nullable Project project) {
        super(project);
        schedulerService = ThemeSchedulerService.getInstance();
        setTitle("Auto Theme Switch");
        init();
        loadSettings();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 获取已安装的主题列表
        List<String> themes = getInstalledThemes();

        int row = 0;

        // ==================== 定时切换区域 ====================
        // 分隔标题
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 4;
        JLabel scheduledLabel = new JLabel("自动主题切换");
        scheduledLabel.setFont(scheduledLabel.getFont().deriveFont(Font.BOLD, 14f));
        mainPanel.add(scheduledLabel, gbc);

        // 启用定时设置
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 4;
        enableCheckBox = new JBCheckBox("启用自动切换");
        mainPanel.add(enableCheckBox, gbc);

        // 主题1设置
        gbc.gridwidth = 1; gbc.gridy = row; gbc.gridx = 0;
        mainPanel.add(new JBLabel("主题一:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        theme1ComboBox = new JComboBox<>(themes.toArray(new String[0]));
        mainPanel.add(theme1ComboBox, gbc);

        // 时间1 - 小时和分钟
        row++;
        gbc.gridwidth = 1; gbc.gridy = row; gbc.gridx = 0;
        mainPanel.add(new JBLabel("时间:"), gbc);

        gbc.gridx = 1;
        JPanel time1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        hour1Spinner = createHourSpinner();
        minute1Spinner = createMinuteSpinner();
        time1Panel.add(hour1Spinner);
        time1Panel.add(new JLabel("时"));
        time1Panel.add(minute1Spinner);
        time1Panel.add(new JLabel("分"));
        gbc.gridwidth = 3;
        mainPanel.add(time1Panel, gbc);

        // 主题2设置
        row++;
        gbc.gridwidth = 1; gbc.gridy = row; gbc.gridx = 0;
        mainPanel.add(new JBLabel("主题二:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        theme2ComboBox = new JComboBox<>(themes.toArray(new String[0]));
        mainPanel.add(theme2ComboBox, gbc);

        // 时间2 - 小时和分钟
        row++;
        gbc.gridwidth = 1; gbc.gridy = row; gbc.gridx = 0;
        mainPanel.add(new JBLabel("时间:"), gbc);

        gbc.gridx = 1;
        JPanel time2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        hour2Spinner = createHourSpinner();
        minute2Spinner = createMinuteSpinner();
        time2Panel.add(hour2Spinner);
        time2Panel.add(new JLabel("时"));
        time2Panel.add(minute2Spinner);
        time2Panel.add(new JLabel("分"));
        gbc.gridwidth = 3;
        mainPanel.add(time2Panel, gbc);

        // 添加间隔
        row++;
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 4;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // ==================== 立即切换区域 ====================
        // 分隔标题
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 4;
        JLabel instantLabel = new JLabel("手动主题切换");
        instantLabel.setFont(instantLabel.getFont().deriveFont(Font.BOLD, 14f));
        mainPanel.add(instantLabel, gbc);

        // 立即切换主题选择和按钮
        gbc.gridy = row; gbc.gridx = 0; gbc.gridwidth = 1;
        mainPanel.add(new JBLabel("选择主题:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        instantThemeComboBox = new JComboBox<>(themes.toArray(new String[0]));
        mainPanel.add(instantThemeComboBox, gbc);

        gbc.gridx = 3; gbc.gridwidth = 1;
        switchNowButton = new JButton("应用");
        switchNowButton.addActionListener(e -> switchThemeNow());
        mainPanel.add(switchNowButton, gbc);

        return mainPanel;
    }

    private JSpinner createHourSpinner() {
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 23, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(80, 25));
        return spinner;
    }

    private JSpinner createMinuteSpinner() {
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(80, 25));
        return spinner;
    }

    private List<String> getInstalledThemes() {
        return Arrays.stream(LafManager.getInstance().getInstalledLookAndFeels())
                .map(UIManager.LookAndFeelInfo::getName)
                .collect(Collectors.toList());
    }

    private void loadSettings() {
        ThemeSettings settings = schedulerService.getSettings();
        enableCheckBox.setSelected(settings.isEnabled());

        if (settings.getTheme1() != null) {
            theme1ComboBox.setSelectedItem(settings.getTheme1());
        }
        if (settings.getTheme2() != null) {
            theme2ComboBox.setSelectedItem(settings.getTheme2());
        }

        hour1Spinner.setValue(settings.getHour1());
        minute1Spinner.setValue(settings.getMinute1());
        hour2Spinner.setValue(settings.getHour2());
        minute2Spinner.setValue(settings.getMinute2());

        // 立即切换默认选择第一个主题
        if (instantThemeComboBox.getItemCount() > 0) {
            instantThemeComboBox.setSelectedIndex(0);
        }
    }

    @Override
    protected void doOKAction() {
        saveSettings();
        super.doOKAction();
    }

    private void saveSettings() {
        ThemeSettings settings = new ThemeSettings();
        settings.setEnabled(enableCheckBox.isSelected());
        settings.setTheme1((String) theme1ComboBox.getSelectedItem());
        settings.setTheme2((String) theme2ComboBox.getSelectedItem());
        settings.setHour1((Integer) hour1Spinner.getValue());
        settings.setMinute1((Integer) minute1Spinner.getValue());
        settings.setHour2((Integer) hour2Spinner.getValue());
        settings.setMinute2((Integer) minute2Spinner.getValue());

        schedulerService.updateSettings(settings);

        if (settings.isEnabled()) {
            schedulerService.startScheduler();
        } else {
            schedulerService.stopScheduler();
        }
    }

    private void switchThemeNow() {
        String selectedTheme = (String) instantThemeComboBox.getSelectedItem();
        if (selectedTheme != null) {
            schedulerService.switchToTheme(selectedTheme);
        }
    }
}