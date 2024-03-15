package com.ms.service.dto;

import com.ms.service.model.ServiceModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO implements Serializable {

    private String serviceID; // id
    @NotBlank
    private String serviceName; // nome

    @NotBlank
    @Email
    private String serviceEmail; // email

    private boolean serviceStatus; // status
    private String serviceDescription; // descrição
    private double servicePrice; // preço
    private int serviceRuntime; // tempo de execução
    private int serviceTerm; // prazo
    private String created;
    private String updated;
    @NotBlank
    private String registryUser;

    public ServiceDTO (ServiceModel serviceModel){
        BeanUtils.copyProperties(serviceModel, this);
    }



}
