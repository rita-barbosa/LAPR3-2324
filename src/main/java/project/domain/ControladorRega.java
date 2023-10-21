package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControladorRega {

   // private static final Integer NUM_DIAS = 30; //É necessário????

    private static final String TODOS_DIAS = "T";

    private static final String DIAS_PARES = "P";

    private static final String DIAS_IMPARES = "I";
    private static final String TRES_DIAS = "3";

    public ControladorRega() {
    }


    /**
     * Utiliza a informaçâo da classe SistemaDeRega e da função checkIsWateringDay() para ver que parcela está a ser regada no momento indicado e quanto tempo falta para a próxima parcela ser regada.
     * @param tempo
     * @param data
     */
    public String checkIsWateringHour(LocalTime tempo, LocalDate data){
        String verification = null;
        Map<String, Integer> regaCheck = checkIsWateringDay(data);

        for( LocalTime timeInPlano : SistemaDeRega.getTempoInicialDeRega()){
            LocalTime temp = timeInPlano;
            for( String parcelaID : regaCheck.keySet()){
                if(tempo.isAfter(temp) && tempo.isBefore(temp.plusMinutes(regaCheck.get(parcelaID)))){
                    verification = "Parcela a ser regada neste momento: "+parcelaID+" | Tempo restante: "+(temp.plusMinutes(regaCheck.get(parcelaID)).getMinute()-temp.getMinute())+" minutos.";
                }else{
                    temp = temp.plusMinutes(regaCheck.get(parcelaID));
                }
            }
        }
        return (verification == null) ? "Não existem parcelas a serem regadas neste momento." : verification;
    }

    /**
     * Utiliza a informaçâo da classe SistemaDeRega para colocar em um LinkedHashMap todas as parcelas que estão a ser regadas no dia indicado.
     * @param data
     * @return regaCheck
     */
    public Map<String,Integer> checkIsWateringDay(LocalDate data){

        Map<String, Integer> regaCheck = new LinkedHashMap<>();

        int difference = data.getDayOfYear() - SistemaDeRega.getInicioDoPlanoDeRega().getDayOfYear() - 1;

        for(Rega rega1 : SistemaDeRega.getPlanoDeRegas()){
            switch (rega1.getRegularidade()){
                case TODOS_DIAS:
                    regaCheck.put(rega1.getIdParcela(), rega1.getTempoRega());
                    break;
                case DIAS_IMPARES:
                    if(data.getDayOfMonth() % 2 == 1){
                        regaCheck.put(rega1.getIdParcela(), rega1.getTempoRega());
                    }
                    break;
                case DIAS_PARES:
                    if(data.getDayOfMonth() % 2 == 0){
                        regaCheck.put(rega1.getIdParcela(), rega1.getTempoRega());
                    }
                    break;
                case TRES_DIAS:
                    if(difference % 3 == 0){
                        regaCheck.put(rega1.getIdParcela(), rega1.getTempoRega());
                    }
                    break;
            }
        }
        return regaCheck;
    }

    /**
     * Se o utilizador não inserir nenhum dia nem hora o processo utiliza o dia e a hora atuais.
    // * @param planoRega
     */
    public String checkIsWateringNoData( /*Set<Rega> planoRega*/){
        return checkIsWateringHour(LocalTime.now(), LocalDate.now());
    }



    private Boolean checkDate(LocalDate data){
        return SistemaDeRega.getInicioDoPlanoDeRega().isBefore(data) && SistemaDeRega.getInicioDoPlanoDeRega().plusDays(30).isAfter(data);
    }

    //Necessário? A hora pode ser qualquer uma :/
    private Boolean checkHour(LocalTime tempo){
        return false;
    }
}
