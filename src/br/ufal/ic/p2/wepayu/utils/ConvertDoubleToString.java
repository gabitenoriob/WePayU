package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.models.empregado.EmpregadoComissionado;

public class ConvertDoubleToString {
    public static String convertDoubleToString(Double value, int decimalPlaces){

     return String.format(("%." + decimalPlaces + "f"), value).replace(".", ",");
    }

}
