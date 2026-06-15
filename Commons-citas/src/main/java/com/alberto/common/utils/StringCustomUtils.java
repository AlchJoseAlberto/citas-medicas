package com.alberto.common.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringCustomUtils {

    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void validarNoVacio(String texto, String mensaje){
        if (texto==null || texto.isBlank())
            throw new IllegalArgumentException(mensaje);
    }


    public static void validarTamanio(String texto,Integer min, Integer max, String mensaje){
        if (texto==null || texto.isBlank())
            throw new IllegalArgumentException(mensaje);
        // se puede reemplazar con validarNoVacio(texto,mensaje)
        if (texto.length() < min || texto.length() > max)
            throw new IllegalArgumentException(mensaje);
    }


public  static  String quitarAcentos(String texto){
        return texto.toLowerCase().replace("á","a")
                .replace("é","e")
                .replace("í","i")
                .replace("ó","o")
                .replace("ú","u");
}

public  static String localDateAString(LocalDate fecha){
        return fecha==null ? null : fecha.format(formato);
}

private static LocalTime StringALocalTime(String hora){
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(hora,formato);
}

public static void validaHoraInicioFin(String horaInicio, String horaFin){
    if (StringALocalTime(horaInicio).isAfter(StringALocalTime(horaFin)))
        throw new IllegalArgumentException("La hora  inicio no puede ser mayor a la hora final");
}

    public static void validaHoraSuperpuestas(
            String horaInicioNueva,
            String horaFinNueva,
            String horaInicioExistente,
            String horaFinExistente
    ) {
        LocalTime inicioNuevo = StringALocalTime(horaInicioNueva);
        LocalTime finNuevo = StringALocalTime(horaFinNueva);
        LocalTime inicioExistente = StringALocalTime(horaInicioExistente);
        LocalTime finExistente = StringALocalTime(horaFinExistente);

        boolean seTraslapan =
                inicioNuevo.isBefore(finExistente) &&
                        finNuevo.isAfter(inicioExistente);

        if (seTraslapan) {
            throw new IllegalArgumentException("El horario se traslapa con otro horario");
        }
    }

/*public static void validaEmail(String email,Integer min, Integer max, String mensaje) {

    validarTamanio(email, min, max, mensaje);

    if (!email.contains("@"))
        throw new IllegalArgumentException("El email no es valido");

}*/


}
