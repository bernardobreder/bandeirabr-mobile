select 
  sum(ora_hash(regi_cd_id)) id,
  sum(ora_hash(regi_sg_regiao)) shortname,
  sum(ora_hash(regi_nm_regiao)) name,
  sum(ora_hash(regi_dt_inicio_valid)) begin_date,
  sum(ora_hash(regi_dt_fim_valid)) end_date
from 
    regiao
where 
    regi_in_interna = 'N'