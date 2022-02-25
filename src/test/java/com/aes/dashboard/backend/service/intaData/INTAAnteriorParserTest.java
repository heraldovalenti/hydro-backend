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

    @Test
    public void parseEnglishResponse() {
        List<INTAAnteriorDataItem> result = INTAAnteriorParser.parseResponse(
"                  Temp     Hi    Low   Out    Dew  Wind  Wind   Wind    Hi    Hi   Wind   Heat    THW                Rain    Heat    Cool    In     In    In     In     In   In Air  Wind  Wind    ISS   Arc.\n" +
"  Fecha   Hora     Out   Temp   Temp   Hum    Pt. Speed   Dir    Run Speed   Dir  Chill  Index  Index   Bar    Rain  Rate    D-D     D-D    Temp   Hum    Dew   Heat    EMC Density  Samp   Tx   Recept  Int.\n" +
"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
"17/02/22   6:00   23.6   24.6   23.6    88   21.5   6.4   NNO   1.61  30.6   ONO   23.6   25.2   25.2  1002.1  9.00 120.0   0.000   0.055   30.9    55   20.8   33.7   9.80 1.1205    348    1    100.0   15 \n" +
"17/02/22   6:15   22.7   23.7   22.7    91   21.1  19.3     O   4.83  33.8     O   21.0   24.1   22.4  1003.2  7.80 149.6   0.000   0.045   30.9    55   20.8   33.7   9.80 1.1220    350    1    100.0   15 ");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(LocalDateTime.of(2022,2,17,9,0), result.get(0).getDate());
        Assertions.assertEquals(LocalDateTime.of(2022,2,17,9,15), result.get(1).getDate());
        Assertions.assertEquals(9, result.get(0).getLluvia());
        Assertions.assertEquals(7.8, result.get(1).getLluvia());
        Assertions.assertEquals(120, result.get(0).getIntensidadLluvia());
        Assertions.assertEquals(149.6, result.get(1).getIntensidadLluvia());
    }

}