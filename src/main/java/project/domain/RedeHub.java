package project.domain;

import org.apache.commons.lang3.ObjectUtils;
import project.structure.Algorithms;
import project.structure.Edge;
import project.structure.EstruturaDeEntregaDeDados;
import project.structure.MapGraph;

import java.time.LocalTime;
import java.util.*;

import static project.structure.Algorithms.shortestPaths;

public class RedeHub {
    private static final RedeHub instance = new RedeHub();

    final private MapGraph<Local, Integer> redeDistribuicao;

    private RedeHub() {
        this.redeDistribuicao = new MapGraph<>(false);
    }

    public static RedeHub getInstance() {
        return instance;
    }

    public void addHub(String numId, Double lat, Double lon) {
        Local vert = new Local(numId, lat, lon);
        redeDistribuicao.addVertex(vert);
    }

    public void addRoute(Local orig, Local dest, Integer distance) {
        redeDistribuicao.addEdge(orig, dest, distance);
    }

    @Override
    public String toString() {
        return redeDistribuicao.toString();
    }

    public MapGraph<Local, Integer> getRedeDistribuicao() {
        return redeDistribuicao;
    }


    public Map<Local, Integer> calculateInfluence(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> influence = new HashMap<>();
        for (Local vertex : graph.vertices()) {
            influence.put(vertex, graph.outDegree(vertex));
        }

        return influence;
    }

    public Map<Local, Integer> calculateProximity(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> proximity = new HashMap<>();

        for (Local vertex : graph.vertices()) {
            Integer proximityValue = calculateVertexProximity(graph, vertex);
            proximity.put(vertex, proximityValue);
        }

        return proximity;
    }

    private Integer calculateVertexProximity(MapGraph<Local, Integer> graph, Local vertex) {
        ArrayList<Integer> dists = new ArrayList<>();
        Algorithms.shortestPaths(graph, vertex, Comparator.naturalOrder(), Integer::sum, 0, new ArrayList<>(), dists);

        int proximitySum = 0;
        for (Integer dist : dists) {
            if (dist != null) {
                proximitySum += dist;
            }
        }

        return proximitySum;
    }

    public Map<Local, Integer> calculateCentrality(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> centrality = Algorithms.betweennessCentrality(graph);

        return centrality;
    }

    public Map<Local, List<Integer>> getTopNMap(Map<Local, List<Integer>> map, Integer n) {
        List<Map.Entry<Local, List<Integer>>> entries = new ArrayList<>(map.entrySet());
        entries.sort((entry1, entry2) -> {
            List<Integer> values1 = entry1.getValue();
            List<Integer> values2 = entry2.getValue();

            int compareCentrality = Integer.compare(values2.get(2), values1.get(2));
            if (compareCentrality != 0) {
                return compareCentrality;
            }

            int compareInfluence = Integer.compare(values2.get(0), values1.get(0));
            if (compareInfluence != 0) {
                return compareInfluence;
            }

            return Integer.compare(values1.get(1), values2.get(1));
        });

        Map<Local, List<Integer>> sortedFinalMap = new LinkedHashMap<>();
        for (Map.Entry<Local, List<Integer>> entry : entries) {
            sortedFinalMap.put(entry.getKey(), entry.getValue());
        }

        return getTopNHubs(sortedFinalMap, n);
    }

    public Map<Local, List<Integer>> getTopNHubs(Map<Local, List<Integer>> map, Integer n) {
        Map<Local, List<Integer>> topNHubsMap = new LinkedHashMap<>();

        int count = 0;
        for (Map.Entry<Local, List<Integer>> entry : map.entrySet()) {
            if (count < n) {
                topNHubsMap.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return topNHubsMap;
    }

    // ----------------------------------------------------US EI07--START--------------------------------------------------------------------------//
    public static EstruturaDeEntregaDeDados calculateBestDeliveryRoute(Local localInicio, LocalTime hora, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga) {
        int hubsAindaAbertosNumero = 0;
        Local tempLocal = localInicio;
        LocalTime tempHora = hora, tempoRestante = LocalTime.of(0, 0), horaInicial = hora;
        List<Local> locaisVisitados = new ArrayList<>();
        LinkedList<Local> tempCaminho = new LinkedList<>(), melhorCaminho = new LinkedList<>();

        while (tempoRestante != null) {
            tempoRestante = null;
            for (Local hub : getHubs()) {
                if (!locaisVisitados.contains(hub)) {
                    LinkedList<Local> caminho = new LinkedList<>();
                    Algorithms.shortestPathWithAutonomy(instance.getRedeDistribuicao(), autonomia, tempLocal, hub, Comparator.naturalOrder(), Integer::sum, 0, caminho);
                    if (getFinishingTimeWithEverything(caminho, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga, getAllHubsInCourse(caminho, locaisVisitados).size()).isBefore(hub.getHorario().getHoraFecho()) && getFinishingTimeWithEverything(caminho, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga, getAllHubsInCourse(caminho, locaisVisitados).size()).isAfter(hub.getHorario().getHoraAbertura())) {
                        if (tempoRestante == null || getStillOpenHubs(getFinishingTimeWithEverything(caminho, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga, getAllHubsInCourse(caminho, locaisVisitados).size())).size() > hubsAindaAbertosNumero && minusTime(hub.getHorario().getHoraFecho(), getFinishingTimeWithEverything(caminho, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga, getAllHubsInCourse(caminho, locaisVisitados).size())).isBefore(tempoRestante)) {
                            tempCaminho = caminho;
                            tempoRestante = minusTime(hub.getHorario().getHoraFecho(), getFinishingTimeWithEverything(caminho, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga, getAllHubsInCourse(caminho, locaisVisitados).size()));
                            tempHora = getFinishingTimeWithEverything(caminho, hora, autonomia, averageVelocity, tempoRecarga, tempoDescarga, getAllHubsInCourse(caminho, locaisVisitados).size());
                            hubsAindaAbertosNumero = getStillOpenHubs(tempHora).size();
                        }
                    }
                }
            }
            if (tempoRestante != null) {
                if (melhorCaminho.isEmpty()) {
                    melhorCaminho.addAll(tempCaminho);
                } else {
                    melhorCaminho.removeLast();
                    melhorCaminho.addAll(tempCaminho);
                }

                tempLocal = melhorCaminho.get(melhorCaminho.size() - 1);
                for (int i = 0; i < getAllHubsInCourse(tempCaminho, locaisVisitados).size(); i++) {
                    locaisVisitados.add(getAllHubsInCourse(tempCaminho, locaisVisitados).get(i));
                }
                hora = tempHora;
            }
        }

        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = analyzeData(autonomia, melhorCaminho);
        estruturaDeEntregaDeDados.setTemposDeChegada(getTimeTable(estruturaDeEntregaDeDados, horaInicial, autonomia, averageVelocity, tempoRecarga, tempoDescarga));
        return estruturaDeEntregaDeDados;
    }


    public static LocalTime getFinishingTimeWithEverything(LinkedList<Local> caminho, LocalTime horaComeco, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga, int numeroDescargas) {
        LocalTime horaFim = LocalTime.of(0,0);
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = analyzeData(autonomia, caminho);
        horaFim = addTime(horaFim, getFinishingTimeRoute(estruturaDeEntregaDeDados.getDistanciaTotal(), averageVelocity,horaComeco));
        for (int i = 0; i < estruturaDeEntregaDeDados.getCarregamentos().size(); i++) {
            horaFim = addTime(horaFim, intToLocalTime(tempoRecarga));
        }
        for (int i = 0; i < numeroDescargas; i++) {
            horaFim = addTime(horaFim, intToLocalTime(tempoDescarga));
        }
        return horaFim;
    }

    public static LocalTime intToLocalTime(int tempo){
        double tempoDouble = (double) tempo / 60;
        LocalTime tempoPercurso = LocalTime.of((int) tempoDouble, (int) ((tempoDouble - (int) tempoDouble) * 60));
        return tempoPercurso;
    }

    public static LocalTime getFinishingTimeRoute(int distanciaTotal ,double averageVelocity, LocalTime horaComeco) {
        LocalTime horaFim = LocalTime.of(0, 0);
        double tempoPercursoDouble = ((double) (distanciaTotal) / 1000) / averageVelocity;
        LocalTime tempoPercurso = LocalTime.of((int) tempoPercursoDouble, (int) ((tempoPercursoDouble - (int) tempoPercursoDouble) * 60));
        horaFim = addTime(horaComeco, tempoPercurso);
        return horaFim;
    }

    public static Map<Local, List<LocalTime>> getTimeTable(EstruturaDeEntregaDeDados estruturaDeEntregaDeDados, LocalTime horaComeco, int autonomia, double averageVelocity, int tempoRecarga, int tempoDescarga) {
        Map<Local, List<LocalTime>> timeTable = new LinkedHashMap<>();
        for (int i = 1; i < estruturaDeEntregaDeDados.getPercurso().size(); i++) {
            LocalTime afterEverything = getFinishingTimeRoute(instance.redeDistribuicao.edge(estruturaDeEntregaDeDados.getPercurso().get(i-1),estruturaDeEntregaDeDados.getPercurso().get(i)).getWeight(), averageVelocity, horaComeco);

            List<LocalTime> listOfTimes = new ArrayList<>();
            if (estruturaDeEntregaDeDados.getCarregamentos().contains(i)) {
                if (estruturaDeEntregaDeDados.getPercurso().get(i).isHub()) {
                    listOfTimes.add(afterEverything.plusMinutes(tempoDescarga));
                    listOfTimes.add(afterEverything.plusMinutes(tempoRecarga));
                    timeTable.put(estruturaDeEntregaDeDados.getPercurso().get(i), listOfTimes);
                    horaComeco = afterEverything.plusMinutes(tempoRecarga).plusMinutes(tempoDescarga);
                } else {
                    listOfTimes.add(afterEverything);
                    listOfTimes.add(afterEverything.plusMinutes(tempoRecarga));
                    timeTable.put(estruturaDeEntregaDeDados.getPercurso().get(i), listOfTimes);
                    horaComeco = afterEverything.plusMinutes(tempoRecarga);
                }
            } else {
                if (estruturaDeEntregaDeDados.getPercurso().get(i).isHub()) {
                    listOfTimes.add(afterEverything);
                    listOfTimes.add(afterEverything.plusMinutes(tempoDescarga));
                    timeTable.put(estruturaDeEntregaDeDados.getPercurso().get(i), listOfTimes);
                    horaComeco = afterEverything.plusMinutes(tempoDescarga);
                } else {
                    listOfTimes.add(afterEverything);
                    listOfTimes.add(afterEverything);
                    timeTable.put(estruturaDeEntregaDeDados.getPercurso().get(i), listOfTimes);
                    horaComeco = afterEverything;
                }
            }
        }
        return timeTable;
    }

    public static List<Local> getAllHubsInCourse(LinkedList<Local> caminho, List<Local> locaisVisitados) {
        List<Local> lista = new ArrayList<>();
        for (int i = 1; i < caminho.size(); i++) {
            if (caminho.get(i).isHub()) {
                lista.add(caminho.get(i));
            }
        }
        return lista;
    }

    public static LocalTime addTime(LocalTime time1, LocalTime time2) {
        return time1.plusHours(time2.getHour()).plusMinutes(time2.getMinute()).plusSeconds(time2.getSecond());
    }

    public static LocalTime minusTime(LocalTime time1, LocalTime time2) {
        return time1.minusHours(time2.getHour()).minusMinutes(time2.getMinute()).minusSeconds(time2.getSecond());
    }

    public static List<Local> getStillOpenHubs(LocalTime hora) {
        List<Local> result = new ArrayList<>();
        for (Local local : getHubs()) {
            if (local.isHub() && local.getHorario().getHoraAbertura().isBefore(hora) && local.getHorario().getHoraFecho().isAfter(hora)) {
                result.add(local);
            }
        }
        return result;
    }

    public static List<Local> getHubs() {
        List<Local> result = new ArrayList<>();
        for (Local local : instance.getRedeDistribuicao().vertices()) {
            if (local.isHub()) {
                result.add(local);
            }
        }
        return result;
    }

// ----------------------------------------------------US EI07--END--------------------------------------------------------------------------//

    public static EstruturaDeEntregaDeDados analyzeData(int autonomia, LinkedList<Local> caminho) {
        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();
        LinkedList<Local> percurso = caminho;
        int distanciaPercorrida = 0, bateria = autonomia;
        boolean flag = true;
        if (percurso != null) {
            for (int i = 0; i < percurso.size() - 1; i++) {
                int distanciaEntrePontos = instance.getRedeDistribuicao().edge(percurso.get(i), percurso.get(i + 1)).getWeight();
                distanciaPercorrida += distanciaEntrePontos;
                if (distanciaEntrePontos > bateria) {
                    if (distanciaEntrePontos <= autonomia) {
                        indexDeCarregamentos.add(i);
                        bateria = autonomia;
                    } else {
                        flag = false;
                    }
                } else {
                    bateria -= distanciaEntrePontos;
                }
            }
        } else {
            flag = false;
            return new EstruturaDeEntregaDeDados(distanciaPercorrida, percurso, indexDeCarregamentos, flag);
        }
        return new EstruturaDeEntregaDeDados(distanciaPercorrida, percurso, indexDeCarregamentos, flag);
    }

    public static LinkedList<Local> getShortestPathForFurthestNodes() {
        int maxDist = 0;
        LinkedList<Local> tempPath = new LinkedList<>();
        for (Local local : instance.getRedeDistribuicao().vertices) {
            ArrayList<LinkedList<Local>> path = new ArrayList<>();
            ArrayList<Integer> dist = new ArrayList<>();

            shortestPaths(instance.getRedeDistribuicao(), local, Comparator.naturalOrder(), Integer::sum, 0, path, dist);
            if (dist.get(getBiggestDist(dist)) > maxDist) {
                maxDist = dist.get(getBiggestDist(dist));
                tempPath = path.get(getBiggestDist(dist));
            }
        }
        return tempPath;
    }

    public static int getBiggestDist(ArrayList<Integer> dist) {
        int temp = 0, index = 0;
        for (int i = 0; i < dist.size(); i++) {
            if (dist.get(i) != null && temp < dist.get(i)) {
                temp = dist.get(i);
                index = i;
            }
        }
        return index;
    }

    public static Local getLocalByID(String id) {
        for (Local local : getInstance().getRedeDistribuicao().vertices) {
            if (local.getNumId().equals(id)) {
                return local;
            }
        }
        return null;
    }

    public ArrayList<LinkedList<Local>> getPathsBetweenLocations(Local org, Local hub) {
        return Algorithms.allPaths(redeDistribuicao, org, hub);
    }

    public ArrayList<ArrayList<Integer>> calculateDistances(ArrayList<LinkedList<Local>> paths) {
        ArrayList<ArrayList<Integer>> distanciasPercurso = new ArrayList<>();
        int defaultValue = -1;
        int distTotal = 0;
        int d;
        int pathsSize = paths.size();

        for (int j = 0; j < pathsSize; j++) {
            LinkedList<Local> list = paths.get(j);
            int listSize = list.size();

            ArrayList<Integer> a = new ArrayList<>();

            for (int i = 0; i < listSize - 1; i++) {
                Edge<Local, Integer> e = redeDistribuicao.edge(list.get(i), list.get(i + 1));
                if (e != null) {
                    d = e.getWeight();
                    distTotal += d;
                    a.add(d);
                } else {
                    a.add(defaultValue);
                }
            }

            a.add(0, distTotal);
            distTotal = 0;
            distanciasPercurso.add(a);
        }

        return distanciasPercurso;
    }

    public ArrayList<Double> calculateTotalTime(ArrayList<ArrayList<Integer>> dists, double veloMedia) {
        ArrayList<Double> velocity = new ArrayList<>();

        for (ArrayList<Integer> d : dists) {
            double velo = d.get(0) / veloMedia;
            velocity.add(velo);
        }

        return velocity;
    }

    public void filterPaths(ArrayList<LinkedList<Local>> paths, ArrayList<ArrayList<Integer>> dists, int autonomia,
                            ArrayList<LinkedList<Local>> newPaths, ArrayList<ArrayList<Integer>> newDists) {
        for (int i = 0; i < paths.size(); i++) {
            if (dists.get(i).get(0) < autonomia) {
                newPaths.add(paths.get(i));
                newDists.add(dists.get(i));
            }
        }
    }
}
