-- Group hosts by CPU number and sort by memory size (descending order):
SELECT cpu_number, id, total_mem
FROM host_info
GROUP BY id
ORDER BY COUNT(total_mem) DESC;

-- Average memory usage in percentages over 5 minute intervals for each host:
-- Function for calculating 5 minute intervals
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts) :: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;


SELECT u.host_id, hostname, round5(u.timestamp),
AVG( (((i.total_mem/1000)-memory_free)/CAST(i.total_mem/1000 AS DECIMAL)) * 100 ) :: int as avg_used_mem_percentage
FROM host_usage u INNER JOIN host_info i ON u.host_id = i.id
GROUP BY round5, host_id, hostname
ORDER BY round5 DESC;


--Detect host failures
--Failure is detect when the server inserts less than 3 data points within a 5-min interval.
SELECT u.host_id, round5(u.timestamp), count(round5(u.timestamp)) as num_data_points
FROM host_usage u INNER JOIN host_info i
ON u.host_id = i.id
GROUP BY u.host_id, round5
HAVING COUNT(round5(u.timestamp))<3
ORDER BY round5;
