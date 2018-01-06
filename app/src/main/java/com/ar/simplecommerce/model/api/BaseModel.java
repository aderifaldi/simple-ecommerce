package com.ar.simplecommerce.model.api;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 12/12/2017.
 */
@NoArgsConstructor
@Data
public class BaseModel implements Serializable {

    private String status;
    private String message;

}
