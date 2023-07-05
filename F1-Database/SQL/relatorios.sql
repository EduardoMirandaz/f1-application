
-- Relatorio 1
SELECT STATUS.STATUS,
	COUNT(*) AS QUANTIDADE_DE_RESULTADOS
FROM RESULTS
JOIN STATUS ON RESULTS.STATUSID = STATUS.STATUSID
GROUP BY STATUS.STATUS;



-- Relatorio 2

CREATE INDEX IF NOT EXISTS idx_geocities15k_lat_long ON geocities15k (lat, long);

CREATE EXTENSION IF NOT EXISTS earthdistance CASCADE;

SELECT lat, long  FROM geocities15k gc WHERE name = 'São Paulo' AND gc.country = 'BR';

SELECT DISTINCT
    geocities15k.name AS nome_cidade,
    airports.iatacode AS codigo_IATA,
    airports.name AS aeroporto,
    airports.city AS cidade_aeroporto,
    earth_distance(
            ll_to_earth(-23.54750, -46.63611),
            ll_to_earth(airports.latdeg, airports.longdeg)
        ) / 1000 AS distancia_em_km,
    airports.type AS tipo_aeroporto
FROM
    geocities15k
        JOIN
    airports ON geocities15k.name = airports.city
WHERE
        geocities15k.country = 'BR'
  AND airports.type IN ('medium_airport', 'large_airport')
  AND earth_distance(
              ll_to_earth(-23.54750, -46.63611),
              ll_to_earth(airports.latdeg, airports.longdeg)
          ) <= 100000 -- limite de distância = 100000 metros (100km)
ORDER BY
    distancia_em_km;




-- Relatorio 3
CREATE INDEX IF NOT EXISTS idx_results_position_constructor ON results (positionorder, constructorid);

CREATE OR REPLACE FUNCTION get_driver_victories(constructor_id INT) RETURNS TABLE (
    nome_piloto TEXT,
    vitorias BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        CONCAT(driver.forename, ' ', driver.surname) AS nome_piloto,
        COUNT(*) AS vitorias
    FROM
        driver
    JOIN
        results ON driver.driverid = results.driverid
    JOIN
        races ON results.raceid = races.raceid
    WHERE
        results.positionorder = 1
        AND results.constructorid = constructor_id
    GROUP BY
        driver.driverid;

    RETURN;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM get_driver_victories(27); 


-- Relatorio 4

CREATE OR REPLACE FUNCTION get_status_counts(constructor_id INT) RETURNS TABLE (
    status TEXT,
    contagem BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        status.status AS status,
        COUNT(*) AS contagem
    FROM
        results
    JOIN
        status ON results.statusid = status.statusid
    WHERE
        results.constructorid = constructor_id
    GROUP BY
        status.statusid;

    RETURN;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM get_status_counts(25);


-- Relatorio 5

CREATE INDEX IF NOT EXISTS idx_results_driver_position ON results (driverid, positionorder);

CREATE OR REPLACE FUNCTION get_pilot_victories(pilot_id INT) RETURNS TABLE (
    ano_corrida INTEGER,
    nome_corrida TEXT,
    vitorias INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        subquery.race_year::integer AS race_year,
        subquery.race_name AS race_name,
        subquery.victories::integer
    FROM (
        SELECT
            EXTRACT(YEAR FROM races.date)::integer AS race_year,
            races.name AS race_name,
            COUNT(*) AS victories
        FROM
            results
        JOIN
            races ON results.raceid = races.raceid
        WHERE
            results.driverid = 102
            AND results.positionorder = 1
        GROUP BY
            ROLLUP (race_year, race_name)
    ) AS subquery;

    RETURN;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM get_pilot_victories(102);




-- Relatório 6

CREATE OR REPLACE FUNCTION get_pilot_status_counts(pilot_id INT) RETURNS TABLE (
    status_name TEXT,
    count BIGINT -- Change the return type to BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
	        status.status AS status_name,
	        COUNT(*) AS count
	    FROM
	        results
	    JOIN
	        status ON results.statusid = status.statusid
	    WHERE
	        results.driverid = pilot_id
	    GROUP BY
	        status.status;
	
	    RETURN;
	END;
	$$ LANGUAGE plpgsql;
	
	SELECT * FROM get_pilot_status_counts(102);
