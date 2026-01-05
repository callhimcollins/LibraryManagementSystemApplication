package com.project.LibraryManagementSystemApplication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private Boolean status;

//    public ApiResponse(String message, Boolean status) {
//        this.message = message;
//        this.status = status;
//    }
}
