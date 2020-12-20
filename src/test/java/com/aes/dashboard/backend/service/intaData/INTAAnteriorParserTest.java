package com.aes.dashboard.backend.service.intaData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class INTAAnteriorParserTest {

    @Test
    public void parse24FormatDateTests() {
        Assertions.assertEquals(LocalDateTime.of(2020,12,12,11,45),
                INTAAnteriorParser.parseDate("12/12/20", "8:45").get());
        Assertions.assertEquals(LocalDateTime.of(2020,12,12,13,30),
                INTAAnteriorParser.parseDate("12/12/20", "10:30").get());
        Assertions.assertEquals(LocalDateTime.of(2020,12,13,2,59),
                INTAAnteriorParser.parseDate("12/12/20", "23:59").get());
    }

    @Test
    public void parseAM_PMFormatTests() {
        Assertions.assertEquals(LocalDateTime.of(2020,12,12,11,45),
                INTAAnteriorParser.parseDate("12/12/20", "8:45a").get());
        Assertions.assertEquals(LocalDateTime.of(2020,12,12,23,45),
                INTAAnteriorParser.parseDate("12/12/20", "8:45p").get());
        Assertions.assertEquals(LocalDateTime.of(2020,12,12,14,11),
                INTAAnteriorParser.parseDate("12/12/20", "11:11a").get());
        Assertions.assertEquals(LocalDateTime.of(2020,12,13,2,11),
                INTAAnteriorParser.parseDate("12/12/20", "11:11p").get());
        Assertions.assertEquals(LocalDateTime.of(2020,12,13,3,00),
                INTAAnteriorParser.parseDate("13/12/20", "12:00a").get());
    }

    @Test
    public void parseEmptyResponseTest() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse("");
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void parseSimpleResponseTest() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse(
"                 Temp.  Temp.  Temp.  Hum.   Pto.   Vel.   Dir.   Rec.  Vel.  Dir.  Sens.   Ind.   Ind.  Indice                  Int.    Rad. Energía  Max.Rad. Grad.D. Grad.D.  Temp.  Hum.  Rocío  In.Cal.          Muest   Tx   Recep.  Int.\n" +
"  Fecha   Hora    Ext.   Máx.   Mín.  Ext.  Rocío Viento Viento Viento  Máx.  Max.  Term.  Calor   THW    THSW    Bar   Lluvia  Lluvia  Solar   Solar    Solar   Calor    Frío    Int.  Int.   Int.   Int.     ET    Viento Viento   ISS   Arc.\n" +
"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
"11/12/20   1:00   21.6   22.5   21.6    61   13.7    0.0    ---   0.00   0.0   ---   21.6   21.3   21.3   19.6    863.9   0.11    0.22     0     0.00       0     0.000   0.134   28.8    37   12.6   28.6     0.03   1318     1     96.3   60 \n" +
"11/12/20   2:00   18.9   21.6   18.5    71   13.5    0.0    ---   0.00   0.0   ---   18.9   19.1   19.1   17.4    863.1   0.33    0.44     0     0.00       0     0.000   0.023   28.6    38   12.8   28.3     0.03   1328     1     97.1   60 ");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(LocalDateTime.of(2020,12,11,4,0), result.get(0).getDate());
        Assertions.assertEquals(0.11, result.get(0).getLluvia());
        Assertions.assertEquals(0.22, result.get(0).getIntensidadLluvia());
        Assertions.assertEquals(LocalDateTime.of(2020,12,11,5,0), result.get(1).getDate());
        Assertions.assertEquals(0.33, result.get(1).getLluvia());
        Assertions.assertEquals(0.44, result.get(1).getIntensidadLluvia());
    }

    @Test
    public void parseResponseWithWrongDateTest() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse(
                "                 Temp.  Temp.  Temp.  Hum.   Pto.   Vel.   Dir.   Rec.  Vel.  Dir.  Sens.   Ind.   Ind.  Indice                  Int.    Rad. Energía  Max.Rad. Grad.D. Grad.D.  Temp.  Hum.  Rocío  In.Cal.          Muest   Tx   Recep.  Int.\n" +
                        "  Fecha   Hora    Ext.   Máx.   Mín.  Ext.  Rocío Viento Viento Viento  Máx.  Max.  Term.  Calor   THW    THSW    Bar   Lluvia  Lluvia  Solar   Solar    Solar   Calor    Frío    Int.  Int.   Int.   Int.     ET    Viento Viento   ISS   Arc.\n" +
                        "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
                        "11/12/20   1:00   21.6   22.5   21.6    61   13.7    0.0    ---   0.00   0.0   ---   21.6   21.3   21.3   19.6    863.9   0.11    0.22     0     0.00       0     0.000   0.134   28.8    37   12.6   28.6     0.03   1318     1     96.3   60 \n" +
                        "13/13/20   2:00   18.9   21.6   18.5    71   13.5    0.0    ---   0.00   0.0   ---   18.9   19.1   19.1   17.4    863.1   0.33    0.44     0     0.00       0     0.000   0.023   28.6    38   12.8   28.3     0.03   1328     1     97.1   60 ");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(LocalDateTime.of(2020,12,11,4,0), result.get(0).getDate());
        Assertions.assertEquals(0.11, result.get(0).getLluvia());
        Assertions.assertEquals(0.22, result.get(0).getIntensidadLluvia());
    }

    @Test
    public void parseResponseWithWrongTimeTest() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse(
                "                 Temp.  Temp.  Temp.  Hum.   Pto.   Vel.   Dir.   Rec.  Vel.  Dir.  Sens.   Ind.   Ind.  Indice                  Int.    Rad. Energía  Max.Rad. Grad.D. Grad.D.  Temp.  Hum.  Rocío  In.Cal.          Muest   Tx   Recep.  Int.\n" +
                        "  Fecha   Hora    Ext.   Máx.   Mín.  Ext.  Rocío Viento Viento Viento  Máx.  Max.  Term.  Calor   THW    THSW    Bar   Lluvia  Lluvia  Solar   Solar    Solar   Calor    Frío    Int.  Int.   Int.   Int.     ET    Viento Viento   ISS   Arc.\n" +
                        "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
                        "11/12/20   1:00   21.6   22.5   21.6    61   13.7    0.0    ---   0.00   0.0   ---   21.6   21.3   21.3   19.6    863.9   0.11    0.22     0     0.00       0     0.000   0.134   28.8    37   12.6   28.6     0.03   1318     1     96.3   60 \n" +
                        "11/12/20   28:00  18.9   21.6   18.5    71   13.5    0.0    ---   0.00   0.0   ---   18.9   19.1   19.1   17.4    863.1   0.33    0.44     0     0.00       0     0.000   0.023   28.6    38   12.8   28.3     0.03   1328     1     97.1   60 ");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(LocalDateTime.of(2020,12,11,4,0), result.get(0).getDate());
        Assertions.assertEquals(0.11, result.get(0).getLluvia());
        Assertions.assertEquals(0.22, result.get(0).getIntensidadLluvia());
    }

    @Test
    public void parseResponseWithEmptyDateTest() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse(
                "                 Temp.  Temp.  Temp.  Hum.   Pto.   Vel.   Dir.   Rec.  Vel.  Dir.  Sens.   Ind.   Ind.  Indice                  Int.    Rad. Energía  Max.Rad. Grad.D. Grad.D.  Temp.  Hum.  Rocío  In.Cal.          Muest   Tx   Recep.  Int.\n" +
                        "  Fecha   Hora    Ext.   Máx.   Mín.  Ext.  Rocío Viento Viento Viento  Máx.  Max.  Term.  Calor   THW    THSW    Bar   Lluvia  Lluvia  Solar   Solar    Solar   Calor    Frío    Int.  Int.   Int.   Int.     ET    Viento Viento   ISS   Arc.\n" +
                        "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
                        "11/12/20   1:00   21.6   22.5   21.6    61   13.7    0.0    ---   0.00   0.0   ---   21.6   21.3   21.3   19.6    863.9   0.11    0.22     0     0.00       0     0.000   0.134   28.8    37   12.6   28.6     0.03   1318     1     96.3   60 \n" +
                        "---        ---    18.9   21.6   18.5    71   13.5    0.0    ---   0.00   0.0   ---   18.9   19.1   19.1   17.4    863.1   0.33    0.44     0     0.00       0     0.000   0.023   28.6    38   12.8   28.3     0.03   1328     1     97.1   60 ");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(LocalDateTime.of(2020,12,11,4,0), result.get(0).getDate());
        Assertions.assertEquals(0.11, result.get(0).getLluvia());
        Assertions.assertEquals(0.22, result.get(0).getIntensidadLluvia());
    }

    @Test
    public void parseResponseWithEmptyLluvia() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse(
                "                 Temp.  Temp.  Temp.  Hum.   Pto.   Vel.   Dir.   Rec.  Vel.  Dir.  Sens.   Ind.   Ind.  Indice                  Int.    Rad. Energía  Max.Rad. Grad.D. Grad.D.  Temp.  Hum.  Rocío  In.Cal.          Muest   Tx   Recep.  Int.\n" +
                        "  Fecha   Hora    Ext.   Máx.   Mín.  Ext.  Rocío Viento Viento Viento  Máx.  Max.  Term.  Calor   THW    THSW    Bar   Lluvia  Lluvia  Solar   Solar    Solar   Calor    Frío    Int.  Int.   Int.   Int.     ET    Viento Viento   ISS   Arc.\n" +
                        "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
                        "11/12/20   1:00   21.6   22.5   21.6    61   13.7    0.0    ---   0.00   0.0   ---   21.6   21.3   21.3   19.6    863.9   ----    0.22     0     0.00       0     0.000   0.134   28.8    37   12.6   28.6     0.03   1318     1     96.3   60 ");
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(LocalDateTime.of(2020,12,11,4,0), result.get(0).getDate());
        Assertions.assertEquals(0, result.get(0).getLluvia());
        Assertions.assertEquals(0.22, result.get(0).getIntensidadLluvia());
    }

}