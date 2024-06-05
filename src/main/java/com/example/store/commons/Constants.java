package com.example.store.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String TIME_ZONE = "America/Bogota";
    public static final String GENERAL_SUCCESS = "Ejecución exitosa";
    public static final String GENERAL_LIST_SUCCESS = "Recursos listados con Exito";
    public static final String GENERAL_CREATE_SUCCESS = "Recurso creado con Exito";
    public static final String GENERAL_UPDATE_SUCCESS = "Recurso actualizado con Exito";
    public static final String GENERAL_DELETE_SUCCESS = "Recurso eliminado con Exito";

    public static final String EXCEPTION_MODEL_NOTFOUND = "Parametro no encontrado";
    public static final String EXCEPTION_MODEL_EMAIL = "Email ya se encuentra registrado";
    public static final String EXCEPTION_MODEL_AMOUNT = "El valor a retirar supera el almacenado";
    public static final String EXCEPTION_MODEL_USER_INVALID = "Usuario no válido";


//    ProcedureCall

    public static final String CALL_INCREASE_QUANTITY = "SELECT aumentar_cantidad_producto(?, ?)";
    public static final String CALL_DECREASE_QUANTITY = "SELECT disminuir_cantidad_producto(?, ?)";
}
