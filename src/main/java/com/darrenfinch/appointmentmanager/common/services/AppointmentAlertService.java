package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppointmentAlertService {
    private final ExecutorService executorService;
    private final TimeHelper timeHelper;
    private final DialogManager dialogManager;
    private final MainRepository mainRepository;

    private boolean hasAlertedUser = false;

    public AppointmentAlertService(ExecutorService executorService, TimeHelper timeHelper, DialogManager dialogManager, MainRepository mainRepository) {
        this.executorService = executorService;
        this.timeHelper = timeHelper;
        this.dialogManager = dialogManager;
        this.mainRepository = mainRepository;
    }

    public void alertUserOfPotentialUpcomingAppointments(int userId) {
        if (hasAlertedUser)
            return;

        executorService.execute(new Task<>() {
            @Override
            protected Object call() throws Exception {
                AtomicBoolean hasUpcomingAppointment = new AtomicBoolean(false);
                for (Appointment appointment : mainRepository.getUpcomingAppointmentsForUser(userId)) {
                    // Ignore appointments before now
                    if (appointment.getStartDateTime().isBefore(timeHelper.systemTimeNow()))
                        continue;

                    ZonedDateTime localAppointmentStartDateTime = appointment.getStartDateTime().withZoneSameInstant(timeHelper.defaultZone());

                    // If the difference between the next appointment and the current time is less than 15 minutes, alert user
                    if (localAppointmentStartDateTime.minusMinutes(Constants.APPOINTMENT_ALERT_THRESHOLD_MINUTES).isEqual(timeHelper.systemTimeNow())
                            || localAppointmentStartDateTime.minusMinutes(Constants.APPOINTMENT_ALERT_THRESHOLD_MINUTES).isBefore(timeHelper.systemTimeNow())) {
                        Platform.runLater(() -> {
                            dialogManager.showAlertDialog(
                                    "You have an upcoming appointment." +
                                            "\nAppointment ID: " + appointment.getId() +
                                            "\nDate/Time: " + localAppointmentStartDateTime.format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT))
                            );
                        });
                        hasUpcomingAppointment.set(true);
                        break;
                    }
                }

                if (!hasUpcomingAppointment.get()) {
                    Platform.runLater(() -> {
                        dialogManager.showAlertDialog("You have no new upcoming appointments.");
                    });
                }

                hasAlertedUser = true;

                return null;
            }
        });
    }
}
