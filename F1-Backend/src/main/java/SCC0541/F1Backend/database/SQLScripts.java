package SCC0541.F1Backend.database;

public class SQLScripts {

    // FINDALLS
    public static final String FIND_ALL_CONSTRUCTORS = "SELECT * FROM CONSTRUCTORS";
    public static final String FIND_ALL_DRIVERS = "SELECT * FROM DRIVER";
    public static final String FIND_ALL_STATUS = "SELECT * FROM STATUS";
    public static final String GET_ALL_AIRPORTS = "SELECT * FROM AIRPORTS";

    //====--===---=-=---===--========--===---=-=---===--====
    // INSERTS
    public static final String LOG_INSERT = "INSERT INTO log_table VALUES (nextval('PUBLIC.SEQ_USER_LOGS_ID'), :id, :idOriginal)";
    public static final String CONSTRUCTOR_INSERT =
            "INSERT INTO CONSTRUCTORS VALUES " +
                    "(nextval('PUBLIC.SEQ_CONSTRUCTOR_ID')," +
                    ":constructorRef," +
                    ":name," +
                    ":nationality," +
                    ":url)";

    public static final String DRIVER_INSERT =
            "INSERT INTO DRIVER VALUES " +
                    "(nextval('PUBLIC.SEQ_DRIVER_ID')," +
                    ":driverRef," +
                    ":number," +
                    ":code," +
                    ":forename," +
                    ":surname," +
                    ":dob," +
                    ":nationality)";


    //====--===---=-=---===--========--===---=-=---===--====
    // GET BY ID
    public static final String FIND_DRIVER_BY_ID = "SELECT * " +
            "FROM DRIVER " +
            "WHERE driverId=:id";

    public static final String FIND_CONSTRUCTOR_BY_ID = "SELECT * " +
            "FROM CONSTRUCTORS " +
            "WHERE constructorId=:id";



    // -=-=-=-=-=-=-----------=-==-=-=
    //              RELATÓRIOS
    public static final String GET_RESULTS_COUNT_BY_STATUS = """
            SELECT STATUS.STATUS,
                   COUNT(*) AS QUANTIDADE_DE_RESULTADOS
            FROM RESULTS
                     JOIN STATUS ON RESULTS.STATUSID = STATUS.STATUSID
            GROUP BY STATUS.STATUS
            ORDER BY  QUANTIDADE_DE_RESULTADOS DESC;""";

    public static final String GET_CLOSE_AIRPORTS = """
            SELECT DISTINCT
                geocities15k.name AS nome_cidade,
                airports.iatacode AS codigo_IATA,
                airports.name AS aeroporto,
                airports.city AS cidade_aeroporto,
                earth_distance(
                        ll_to_earth(airports.latdeg, airports.longdeg),
                        ll_to_earth(:latitude, :longitude)
                    ) / 1000 AS distancia_em_km,
                airports.type AS tipo_aeroporto
            FROM
                geocities15k
                    RIGHT JOIN
                airports ON earth_distance(
                          ll_to_earth(:latitude, :longitude),
                          ll_to_earth(airports.latdeg, airports.longdeg)
                      ) <= 100000 -- limite de distância = 100000 metros (100km)
            WHERE
                    airports.isocountry = 'BR'
                    AND airports.type IN ('medium_airport', 'large_airport')
                    AND geocities15k.lat = :latitude
                    AND geocities15k.long = :longitude
            ORDER BY
                distancia_em_km;
            """;

    public static final String FIND_CITY_BY_NAME = "SELECT * FROM GEOCITIES15K WHERE geocities15k.name=:nomeCidade";
}
