package com.darrenfinch.appointmentmanager.data.models;

import java.util.Date;

public record Appointment(int id, String title, String description, String location, String type, Date startDate,
                          Date endDate, int customerId, int userId, int contactId) {}
