package com.sph.autothemeswitch.model;


public class ThemeSettings {
    private boolean enabled;
    private String theme1;
    private String theme2;
    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTheme1() {
        return theme1;
    }

    public void setTheme1(String theme1) {
        this.theme1 = theme1;
    }

    public String getTheme2() {
        return theme2;
    }

    public void setTheme2(String theme2) {
        this.theme2 = theme2;
    }

    public int getHour1() {
        return hour1;
    }

    public void setHour1(int hour1) {
        this.hour1 = hour1;
    }

    public int getMinute1() {
        return minute1;
    }

    public void setMinute1(int minute1) {
        this.minute1 = minute1;
    }

    public int getHour2() {
        return hour2;
    }

    public void setHour2(int hour2) {
        this.hour2 = hour2;
    }

    public int getMinute2() {
        return minute2;
    }

    public void setMinute2(int minute2) {
        this.minute2 = minute2;
    }
}