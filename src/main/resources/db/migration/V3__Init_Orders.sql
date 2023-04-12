DO $$
DECLARE
    i INTEGER := 1;
BEGIN
    WHILE i <= 100 LOOP

        INSERT INTO hunting_orders (id, date, hunting_order_type_id, person_id, status_id)
        SELECT
            i,
            (DATE '2023-04-01' + FLOOR(RANDOM() * 90) * INTERVAL '1 day'),
            (SELECT id FROM hunting_order_types ORDER BY random() LIMIT 1),
            (SELECT id FROM persons ORDER BY random() LIMIT 1),
            (SELECT id FROM statuses WHERE id = 1);

        INSERT INTO hunting_order_resources(id, amount, district_id, resource_id, status_id)
        SELECT
            i,
            (SELECT FLOOR(random() * 5) + 1),
            (SELECT id FROM districts ORDER BY random() LIMIT 1),
            (SELECT id FROM resources ORDER BY random() LIMIT 1),
            (SELECT id FROM statuses WHERE id = 1);

        i := i + 1;
    END LOOP;
END $$;

DO $$
DECLARE
    i INTEGER := 1;
    j INTEGER;
    rnd INTEGER;
BEGIN
    WHILE i <= 100 LOOP
    rnd := FLOOR(random() * 3) + 1;
    j := 1;
WHILE j <= rnd LOOP
      INSERT INTO hunting_orders_hunting_order_resources (hunting_order_id, hunting_order_resource_id)
      SELECT i,
      (SELECT id FROM hunting_order_resources ORDER BY random() LIMIT 1);
      j := j + 1;
END LOOP;
i := i + 1;
END LOOP;
END $$;