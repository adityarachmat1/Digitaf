package com.drife.digitaf.ORM.Controller;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.CarType;
import com.orm.query.Select;
import com.orm.util.NamingHelper;

import java.util.ArrayList;
import java.util.List;

public class CarController {
    public static String TAG = CarController.class.getSimpleName();

    public static boolean bulkInsertCar(List<CarModel> carModels){
        try {
            if(carModels != null){
                for (int i=0; i<carModels.size(); i++){
                    CarModel carModel = carModels.get(i);
                    String CarModelId = carModel.getId();
                    String CarName = carModel.getName();
                    String CarDescription = carModel.getDescription();
                    String CarCode = carModel.getCode();
                    String BrandId = carModel.getBrand().getId();
                    String BrandCode = carModel.getBrand().getCode();
                    String Brand = carModel.getBrand().getName();
                    String CategoryId = carModel.getCategory().getId();
                    String CategoryCode = carModel.getCategory().getCode();
                    String Category = carModel.getCategory().getName();
                    String CategoryGroupId = carModel.getCategory_group().getId();
                    String CategoryGroupCode = carModel.getCategory_group().getCode();
                    String CategoryGroup = carModel.getCategory_group().getName();

                    com.drife.digitaf.ORM.Database.CarModel car = new com.drife.digitaf.ORM.Database.CarModel(CarModelId,
                            CarName,CarDescription,CarCode,BrandId,BrandCode,Brand,CategoryId,CategoryCode,Category,CategoryGroupId,
                            CategoryGroupCode,CategoryGroup);
                    car.save();
                }
            }
            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCar","success insert car");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertCar","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllCar(){
        try {
            com.drife.digitaf.ORM.Database.CarModel.deleteAll(com.drife.digitaf.ORM.Database.CarModel.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllCar","success clear car");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllCar","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean bulkInsertCarType(List<CarModel> carModels){
        try {
            if(carModels != null){
                for (int i=0; i<carModels.size(); i++){
                    CarModel carModel = carModels.get(i);
                    String CarModelId = carModel.getId();
                    String CarModelCode = carModel.getCode();
                    String CarModelName = carModel.getName();
                    List<CarType> carTypes = carModel.getCar_type();
                    for (int j=0; j<carTypes.size(); j++){
                        CarType carType = carTypes.get(j);
                        String CarTypeId = carType.getId();
                        String Code = carType.getCode();
                        String Name = carType.getName();
                        String Description = carType.getDescription();

                        com.drife.digitaf.ORM.Database.CarType type = new com.drife.digitaf.ORM.Database.CarType(CarTypeId,
                                Code,Name,Description,CarModelId,CarModelCode,CarModelName);
                        type.save();
                    }
                }
            }

            LogUtility.logging(TAG,LogUtility.infoLog,"bulkInsertCarType","success insert car type");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"bulkInsertCarType","Exception",e.getMessage());
            return false;
        }
    }

    public static boolean removeAllCarType(){
        try {
            com.drife.digitaf.ORM.Database.CarType.deleteAll(com.drife.digitaf.ORM.Database.CarType.class);
            LogUtility.logging(TAG,LogUtility.infoLog,"removeAllCarType","success clear car");
            return true;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"removeAllCarType","Exception",e.getMessage());
            return false;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CarModel> getAllCar(){
        try {
            List<com.drife.digitaf.ORM.Database.CarModel> carModels = com.drife.digitaf.ORM.Database.CarModel.listAll(com.drife.digitaf.ORM.Database.CarModel.class);
            return carModels;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllCar","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CarModel> getCarModels(String carCode){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("CarModel")+" where " +
                    NamingHelper.toSQLNameDefault("CarCode")+" = '"+carCode+"'";
            List<com.drife.digitaf.ORM.Database.CarModel> resultList =
                    com.drife.digitaf.ORM.Database.CarType.findWithQuery(com.drife.digitaf.ORM.Database.CarModel.class,query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getCarModels","resultList",JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getCarModels","Exception",e.getMessage());
            return null;
        }
    }

    public static List<com.drife.digitaf.ORM.Database.CarType> getAllCarType(String CarModelCode){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("CarType")+" where " +
                    NamingHelper.toSQLNameDefault("CarModelCode")+" = '"+CarModelCode+"'";
            List<com.drife.digitaf.ORM.Database.CarType> resultList =
                    com.drife.digitaf.ORM.Database.CarType.findWithQuery(com.drife.digitaf.ORM.Database.CarType.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllCarType","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllCarType","Exception",e.getMessage());
            return null;
        }
    }


    public static List<com.drife.digitaf.ORM.Database.CarType> getAllCarTypeName(String CarTypeId){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("CarType")+" where " +
                    NamingHelper.toSQLNameDefault("Code")+" = '"+CarTypeId+"'";
            List<com.drife.digitaf.ORM.Database.CarType> resultList =
                    com.drife.digitaf.ORM.Database.CarType.findWithQuery(com.drife.digitaf.ORM.Database.CarType.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllCarType","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllCarType","Exception",e.getMessage());
            return null;
        }
    }
    public static List<com.drife.digitaf.ORM.Database.CarModel> getAllAvailableCarInPackage(String CategoryGroupId){
        try {
            String query = "select * from "+ NamingHelper.toSQLNameDefault("CarModel")+" where " +
                    NamingHelper.toSQLNameDefault("CategoryGroupId")+" = '"+CategoryGroupId+"'";
            List<com.drife.digitaf.ORM.Database.CarModel> resultList =
                    com.drife.digitaf.ORM.Database.CarType.findWithQuery(com.drife.digitaf.ORM.Database.CarModel.class, query);
            LogUtility.logging(TAG,LogUtility.infoLog,"getAllAvailableCarInPackage","resultList", JSONProcessor.toJSON(resultList));
            return resultList;
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getAllAvailableCarInPackage","Exception",e.getMessage());
            return null;
        }
    }

    public static List<String> getCategoryGroupInPackage(String packageCode){
        List<String> categoryGroups = new ArrayList<>();
        try{
            Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                    .where(NamingHelper.toSQLNameDefault("PackageCode") +" = "+"'"+packageCode+"' and "+
                            NamingHelper.toSQLNameDefault("CategoryGroupId") +" != "+"'0'")
                    .groupBy(NamingHelper.toSQLNameDefault("CategoryGroupId"));

            List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();

            for (int i=0; i<resultList.size(); i++){
                String categoryGroupId = resultList.get(i).getCategoryGroupId();
                categoryGroups.add(categoryGroupId);
            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"getCategoryGroupInPackage",e.getMessage());
        }
        LogUtility.logging(TAG,LogUtility.infoLog,"getCategoryGroupInPackage","categoryGroups", JSONProcessor.toJSON(categoryGroups));
        return categoryGroups;
    }

    public static List<String> getCarInPackage(String packageCode){
        List<String> cars = new ArrayList<>();
        Select select = Select.from(com.drife.digitaf.ORM.Database.PackageRule.class)
                .where(NamingHelper.toSQLNameDefault("PackageCode") +" = "+"'"+packageCode+"' and "+
                        NamingHelper.toSQLNameDefault("CategoryGroupId") +" == "+"'0'")
                .groupBy(NamingHelper.toSQLNameDefault("CarModelCode"));

        List<com.drife.digitaf.ORM.Database.PackageRule> resultList = select.list();

        for (int i=0; i<resultList.size(); i++){
            String carModel = resultList.get(i).getCarModelCode();
            cars.add(carModel);
        }
        LogUtility.logging(TAG,LogUtility.infoLog,"getCarInPackage","cars", JSONProcessor.toJSON(cars));

        return cars;
    }

    public static List<String> getCarCategoryGroup(String carCode){
        List<String> categoryGroups = new ArrayList<>();
        Select select = Select.from(com.drife.digitaf.ORM.Database.CarModel.class)
                .where(NamingHelper.toSQLNameDefault("CarCode") +" = "+"'"+carCode+"'")
                .groupBy(NamingHelper.toSQLNameDefault("CategoryGroupCode"));

        List<com.drife.digitaf.ORM.Database.CarModel> resultList = select.list();
        LogUtility.logging(TAG,LogUtility.infoLog,"getCarCategoryGroup","resultList",JSONProcessor.toJSON(resultList));
        for (int i=0; i<resultList.size(); i++){
            com.drife.digitaf.ORM.Database.CarModel model = resultList.get(i);
            String catGroup = model.getCategoryGroupCode();
            categoryGroups.add(catGroup);
        }
        return categoryGroups;
    }
}
