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

    /**
     * Constructs the appointment alert service.
     */
    public AppointmentAlertService(ExecutorService executorService, TimeHelper timeHelper, DialogManager dialogManager, MainRepository mainRepository) {
        this.executorService = executorService;
        this.timeHelper = timeHelper;
        this.dialogManager = dialogManager;
        this.mainRepository = mainRepository;
    }

    /**
     * Alerts the user with a dialog box if there are any upcoming appointments within the time frame specified by <code>Constants.APPOINTMENT_ALERT_THRESHOLD_MINUTES</code>.
     *
     * This method uses 2 Runnable lambdas to improve the readability, maintainability, and conciseness of the code.
     * One of them shows the alert dialog if there are upcoming appointments, the other shows the alert dialog that says there are no upcoming appointments.
     */
    public void alertUserOfPotentialUpcomingAppointments(int userId) {
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

                return null;
            }
        });
    }
}
