package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class CarType extends SugarRecord implements Serializable {
    private String CarTypeId;
    private String Code;
    private String Name;
    private String Description;
    private String CarModelId;
    private String CarModelCode;
    private String CarModelName;

    public CarType() {
    }

    public CarType(String carTypeId, String code, String name, String description, String carModelId, String carModelCode, String carModelName) {
        CarTypeId = carTypeId;
        Code = code;
        Name = name;
        Description = description;
        CarModelId = carModelId;
        CarModelCode = carModelCode;
        CarModelName = carModelName;
    }

    public String getCarModelCode() {
        return CarModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        CarModelCode = carModelCode;
    }

    public String getCarModelName() {
        return CarModelName;
    }

    public void setCarModelName(String carModelName) {
        CarModelName = carModelName;
    }

    public String getCarTypeId() {
        return CarTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        CarTypeId = carTypeId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCarModelId() {
        return CarModelId;
    }

    public void setCarModelId(String carModelId) {
        CarModelId = carModelId;
    }

    @Override
    public String toString() {
        return Name;
    }
}
