package com.cossinest.homes.payload.messages;

public class ErrorMessages {

    private ErrorMessages(){}

    public static final String NOT_FOUND_USER_EMAIL="There is no email that matches %s";
    public static final String BUILT_IN_USER_CAN_NOT_UPDATE="BuiltIn user can not update";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String CONFLICT_TOUR_TIME = "Required tour time is already booked";
}

