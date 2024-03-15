package com.ms.service.dto;

import com.ms.service.model.ServiceModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ServiceDTO implements Serializable {

    private String serviceID; // id
    @NotBlank
    //    @Pattern(regexp = "^[A-Z][a-z]+\s[A-Z][a-z]+$",
//            message = "O nome completo deve conter: " +
//                    "Nome e sobrenome com iniciais em Letra Maiúscula!")
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
    private String registryService;

    public ServiceDTO (ServiceModel serviceModel){
        BeanUtils.copyProperties(serviceModel, this);
    }
    public ServiceDTO(){
        super();
    }


}
