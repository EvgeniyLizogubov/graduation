package com.github.EvgeniyLizogubov.voteRestaurantRESTApi.error;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg);
    }
}