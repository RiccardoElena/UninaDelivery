SELECT *
from account
ORDER BY RANDOM()
LIMIT 1;
SELECT *
from "Order";
begin;
SELECT setval(
    '"Order_orderid_seq"',
    (
      SELECT MAX(orderid)
      FROM "Order"
    )
  );
INSERT INTO "Order" (email, name, supplier, quantity)
SELECT a.email,
  s.name,
  s.supplier,
  CEIL(s.quantity * RANDOM())
FROM account a,
  stores s
where EXISTS (
    SELECT 1
    from deposit
    WHERE depositid = s.depositid
      and isSameCity(a.zipcode, a.country, zipcode, country)
  )
ORDER BY RANDOM()
LIMIT 20;
UPDATE "Order"
set extrawarranty = FLOOR(6 * RANDOM())
where orderid >= 53;
SELECT *
from "Order";
rollback;
update "Order"
set isExpress = true
where orderid in (
    select orderid
    from "Order"
    where orderid >= 53
    order by RANDOM()
    limit 10
  );
SELECT *
from "Order";
update "Order"
set iscompleted = FALSE
where orderid = 1