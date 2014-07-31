package com.example.Search_and_Destroy_SMS;

/**
 * Created by msinst on 7/28/2014.
 */
public class SMS {

        String id;
        String body;
        String number;

        //constructor
        public SMS(String newId, String newBody, String newNumber) {
            id = newId;
            body = newBody;
            number = newNumber;
        }

    @Override
    public String toString() {
        return this.body;
    }

}
