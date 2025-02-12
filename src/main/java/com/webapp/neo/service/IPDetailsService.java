package com.webapp.neo.service;

import javax.servlet.http.HttpServletRequest;

public interface IPDetailsService {
    String ipGrab(HttpServletRequest request);
    void saveIPDetails(String ip);
}


