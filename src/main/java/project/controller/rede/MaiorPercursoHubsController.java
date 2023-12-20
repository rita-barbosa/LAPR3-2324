package project.controller.rede;

import project.domain.Local;
import project.domain.RedeHub;
import project.structure.Algorithms;
import project.structure.EstruturaDeEntregaDeDados;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MaiorPercursoHubsController {

    public static EstruturaDeEntregaDeDados calculateBestDeliveryRoute(Local localInicio, LocalTime hora, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga) {
        return RedeHub.calculateBestDeliveryRoute(localInicio,hora,autonomia,averageVelocity,tempoRecarga,tempoDescarga);
    }

    public static LocalTime getFinishingTimeRoute(EstruturaDeEntregaDeDados estruturaDeEntregaDeDados, LocalTime horaComeco, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga){
        return RedeHub.getFinishingTimeRoute(estruturaDeEntregaDeDados,horaComeco,autonomia,averageVelocity,tempoRecarga,tempoDescarga);
    }
}
