package com.cossinest.homes.payload.messages;

public class ErrorMessages {




    public static final String NOT_FOUND_TOUR_REQUEST = "%s id'li Tour request is not found";
    public static final String TOUR_REQUEST_CAN_NOT_BE_CHANGED = "Tour reuest is already approved. It can not be changed";;


    private ErrorMessages() {
    }
    public static final String RESET_PASSWORD_CODE_DID_NOT_MATCH = "The reset password code didn`t match";
    public static final String PASSWORDS_DID_NOT_MATCH ="Password did not match" ;

    public static final String THERE_IS_NO_USER_REGISTERED_WITH_THIS_EMAIL_ADRESS = "There is no user registered with this email adress";

    public static final String LOW_ROLE_USERS_CAN_NOT_DELETE_HIGH_ROLE_USERS = "Low role users can not delete high role users";

    public static final String USER_IS_NOT_FOUND = "The user with %s id is not found";
    public static final String ADMIN_OR_MANAGER_CAN_NOT_USE_THIS_METHOD = "Admin or manager can not use this method";


    public static final String NOT_HAVE_AUTHORITY = "You don't have authority";


    public static final String USER_HAS_NOT_CUSTOMER_ROLE = "User is not customer";
    public static final String THE_PASSWORDS_ARE_NOT_MATCHED = "The passwords entered do not match. Please re-enter your password";
    public static final String PASSWORD_IS_INCCORECT = "Password is incorrect";
    public static final String THIS_EMAIL_IS_ALREADY_TAKEN = "This email %s is already taken";
    public static final String THIS_PHONE_NUMBER_IS_ALREADY_TAKEN = "This phone %s number is already taken";

    public static final String NOT_FOUND_USER_EMAIL = "There is no email that matches %s";
    public static final String BUILT_IN_USER_CAN_NOT_BE_UPDATED = "BuiltIn user can not be updated";
    public static final String PASSWORD_IS_NOT_CORRECT = "Password do not matched";
    public static final String EMAIL_IS_INCORRECT = "Email %s is not correct";
    public static final String BUILT_IN_USER_CAN_NOT_BE_DELETED = "BuiltIn user can not be deleted";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String CONFLICT_TOUR_TIME = "Required tour time is already booked. Try to book it with time 30 minutes late";


    //Advert
    public static final String ADVERT_NOT_FOUND="Advert not found";
    public static final String START_PRICE_AND_END_PRICE_INVALID="Start price must be less than end price";

    public static final String CATEGORY_NOT_FOUND= "Categoty not found";
    public static final String CITY_NOT_FOUND= "City not found";
    public static final String COUNTRY_NOT_FOUND= "Country not found";


}

