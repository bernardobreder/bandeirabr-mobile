select 
    sum(ora_hash(gapr_cd_id_pai)) parent_id, 
    sum(ora_hash(gapr_cd_id_filho)) child_id, 
    sum(ora_hash(agag_dt_inicio_valid)) begin_date, 
    sum(ora_hash(agag_dt_fim_valid)) end_date 
from 
    agregacao_agregacao 