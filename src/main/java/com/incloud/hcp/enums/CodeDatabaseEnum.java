package com.incloud.hcp.enums;

/**
 * Created by Administrador on 09/11/2017.
 */
public enum CodeDatabaseEnum {
    CODE_10001(10001, "El proveedor con ruc no existe"),
    CODE_10002(10002, "Existe una solicitud pendiente de aprobación"),
    CODE_10003(10003, "El proveedor ya fue observado"),
    CODE_10004(10004, "Existe una solicitud pendiente de aprobación"),
    CODE_10005(10005, "El proveedor no está observado"),

    CODE_10006(10006, "UD, tiene una solicitud pendiente"),
    CODE_10007(10007, "UD, tiene una solicitud aprobada"),
    CODE_10008(10008, "UD, tiene una solicitud pendiente con un RUC diferente"),
    CODE_10009(10009, "UD, tiene una solicitud aprobada con un RUC diferente"),
    CODE_10010(10010, "El RUC fue registrado por otro proveedor");

    private int code;
    private String msg;
    CodeDatabaseEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code){
        for(CodeDatabaseEnum x : values()){
            if(x.getCode() == code){
                return x.getMsg();
            }
        }

        return "Error, consulte el LOG del sistema";
    }

    private int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
