package com.darrenfinch.appointmentmanager.data.models;

import java.util.Date;

public record LogMetaData(Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy) {}
