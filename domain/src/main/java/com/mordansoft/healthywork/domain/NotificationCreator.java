package com.mordansoft.healthywork.domain;

import com.mordansoft.healthywork.domain.model.Exercise;

public interface NotificationCreator {
    String ACTION_SNOOZE = "com.mordansoft.healthywork";
    int NOTIFY_ID = 102;
    String CHANNEL_ID = "HealthWork channel";
    public boolean createNotification(Exercise exercise);
    public boolean deleteNotification();
}
