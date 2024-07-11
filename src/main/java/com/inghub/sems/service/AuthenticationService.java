package com.inghub.sems.service;

import com.inghub.sems.model.AuthenticationRequest;
import com.inghub.sems.model.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
