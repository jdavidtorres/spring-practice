package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "beanDemo")
@SessionScoped
@Getter
@Setter
public class BeanDemo implements Serializable {

    private String demoText = "Hello World";
    private String selectedOption;
}
