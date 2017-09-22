select 
    sum(ora_hash(regi_cd_id_pai)) parent_id,
    sum(ora_hash(regi_cd_id_filho)) child_id,
    sum(ora_hash(rere_dt_inicio_valid)) begin_date,
    sum(ora_hash(rere_dt_fim_valid)) end_date
from 
    regiao_regiao