package com.ms.service.service;

import com.ms.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceServices {
    @Autowired
    ServiceRepository serviceRepository;

}
