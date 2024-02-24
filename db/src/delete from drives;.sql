delete from drives;
delete from ships;
UPDATE "Order"
set iscompleted = NULL;
delete from shipment
where shipmentid = 30;
select *
from shipment;
begin;
insert into ships
VALUES (29, 6);
rollback;
select *
from stores
where depositid = 1;
CREATE or replace FUNCTION uninadelivery.checkdeposithasenoughproducts(
    DIDend integer,
    DIDstart integer,
    PName text,
    PSupply text,
    QTY integer
  ) RETURNS void LANGUAGE plpgsql AS $$
DECLARE QTY2 stores.quantity %TYPE;
BEGIN -- if shipment is towards a client then
IF DIDend IS NULL THEN IF NOT EXISTS (
  SELECT 1
  FROM stores
  WHERE depositid = DIDstart
    AND name = PName
    AND supplier = PSupply
    AND quantity >= QTY
) THEN -- all the products must be in the same deposit
perform raise_custom_error('E0013');
ELSE -- if nothing went wrong, decrement the quantity in the starting deposit
UPDATE stores
SET quantity = GREATEST(quantity - QTY, 0)
WHERE depositid = DIDstart
  AND name = PName
  AND supplier = PSupply;
END IF;
ELSE -- otherwise
-- retrieve maximum quantity of the product in the starting deposit
SELECT stores.quantity INTO Qty2
FROM stores
WHERE depositid = DIDstart
  AND name = PName
  AND supplier = PSupply;
-- if there is no product exception 'E0013' is raised otherwise
-- transfer the maximum quantity possible to the new deposit
INSERT INTO stores (name, supplier, depositid, quantity)
VALUES (PName, PSupply, DIDend, LEAST(QTY, QTY2)) ON CONFLICT (name, supplier, depositid) DO
UPDATE
SET quantity = stores.quantity + EXCLUDED.quantity;
-- if nothing went wrong, decrement the quantity in the starting deposit
UPDATE stores
SET quantity = GREATEST(quantity - QTY, 0)
WHERE depositid = DIDstart
  AND name = PName
  AND supplier = PSupply;
END IF;
EXCEPTION
WHEN no_data_found THEN perform raise_custom_error('E0013');
END;
$$;