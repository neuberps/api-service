package com.ms.service.service;

import com.ms.service.dto.ServiceDTO;
import com.ms.service.exceptions.ServiceNotFoundException;
import com.ms.service.model.ServiceModel;
import com.ms.service.exceptions.ServiceException;
import com.ms.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class ServiceServices {
    @Autowired
    ServiceRepository serviceRepository;

    public List<ServiceDTO> findAll() throws ServiceException {
        List<ServiceModel> list = serviceRepository.findAll();
        if(list.isEmpty()){
            throw new ServiceNotFoundException("Services not found");
        }
        return list.stream().map(ServiceDTO::new).toList();
    }

    public ServiceDTO create(ServiceDTO serviceDTO) throws ServiceException {
        ServiceModel serviceModel = new ServiceModel(serviceDTO);
        serviceModel.setRegistryService(serviceDTO.getRegistryService());
        serviceModel.setCreated(LocalDateTime.now().toString());
        serviceRepository.save(serviceModel);
        return new ServiceDTO(serviceModel);
    }

    public ServiceDTO findById(String id) throws ServiceException {
        return serviceRepository.findById(id)
                .map(ServiceDTO::new)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID: " + id));
    }

    public ServiceDTO findByServiceName(String name) throws ServiceException {
        return serviceRepository.findByServiceName(name)
                .map(ServiceDTO::new)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with NAME: " + name));
    }

    public ServiceDTO update(String id, ServiceDTO serviceDTO) throws ServiceException {
        Optional<ServiceModel> optionalServiceModel = serviceRepository.findById(id);
        if (optionalServiceModel.isPresent()){
            ServiceModel serviceModel = optionalServiceModel.get();

            serviceModel.setServiceName(serviceDTO.getServiceName());
            serviceModel.setServiceEmail(serviceDTO.getServiceEmail());
            serviceModel.setServiceStatus(serviceDTO.isServiceStatus());
            serviceModel.setServiceDescription(serviceDTO.getServiceDescription());
            serviceModel.setServicePrice(serviceDTO.getServicePrice());
            serviceModel.setServiceRuntime(serviceDTO.getServiceRuntime());
            serviceModel.setServiceTerm(serviceDTO.getServiceTerm());
            serviceModel.setRegistryService(serviceDTO.getRegistryService());
            serviceModel.setUpdated(LocalDateTime.now().toString());

            serviceRepository.save(serviceModel);
            return new ServiceDTO(serviceModel);
        } else {
            throw new ServiceNotFoundException("Service not found with ID: " + id);
        }
    }

    public void delete(String id) throws ServiceException {
        if(!serviceRepository.existsById(id)){
            throw new ServiceNotFoundException("Service not found with ID:" + id);
        }
        serviceRepository.deleteById(id);
    }

}
