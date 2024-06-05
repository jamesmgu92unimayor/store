package com.example.store.auth.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    String token; 
}
