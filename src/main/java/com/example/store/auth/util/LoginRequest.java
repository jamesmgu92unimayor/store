package com.example.store.auth.util;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginRequest {
    String username;
    String password; 
}
