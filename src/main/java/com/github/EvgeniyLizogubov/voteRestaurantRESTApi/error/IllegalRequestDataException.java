package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.error;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}