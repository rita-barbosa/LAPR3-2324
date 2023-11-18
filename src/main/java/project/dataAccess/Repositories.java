package project.dataAccess;

public class Repositories {

    private static final Repositories instance = new Repositories();
    private OperacaoRepository operacaoRepository;

    private UnitsRepository unitsRepository;
    private FieldsRepository fieldsRepository;
    private CultureRepository cultureRepository;
    private DatesRepository datesRepository;

    public UnitsRepository getUnitsRepository() {
        return unitsRepository;
    }

    public FieldsRepository getFieldsRepository() {
        return fieldsRepository;
    }

    public CultureRepository getCultureRepository() {
        return cultureRepository;
    }

    private Repositories() {
        operacaoRepository = new OperacaoRepository();
        unitsRepository = new UnitsRepository();
        fieldsRepository = new FieldsRepository();
        cultureRepository = new CultureRepository();
        datesRepository = new DatesRepository();
    }

    public static Repositories getInstance() {
        return instance;
    }

    public OperacaoRepository getOperacaoRepository() {
        return operacaoRepository;
    }

    public DatesRepository getDatesRepository(){
        return datesRepository;
    }

}