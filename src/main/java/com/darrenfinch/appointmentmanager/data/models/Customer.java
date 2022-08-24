package com.darrenfinch.appointmentmanager.data.models;

public record Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId) {}