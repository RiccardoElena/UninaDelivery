select *
from account
where email = 'giorgionecapitone@gmail.com'
create or replace function isDirectionCorrect(
    zc area.zipcode %type,
    cy area.country %type,
    sdate date,
    tid transport.transportid %type
  ) returns boolean as $$ begin return case
    when exists (
      select 1
      from covers
      where (
          zipcode <> zc
          or country <> cy
        )
        and date = sdate
        and transportid = tid
    ) then false
    else true
  end;
end;
$$ language plpgsql;