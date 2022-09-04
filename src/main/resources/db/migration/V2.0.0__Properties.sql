ALTER TABLE items
    ADD properties jsonb;
ALTER TABLE items
    DROP COLUMN quantity_amount;
ALTER TABLE items
    DROP COLUMN quantity_unit;
