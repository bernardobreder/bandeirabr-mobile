select 
    gapr_cd_id_pai parent_id, 
    gapr_cd_id_filho child_id, 
    agag_dt_inicio_valid begin_date, 
    agag_dt_fim_valid end_date 
from 
    agregacao_agregacao 
order by 
    parent_id,
    agag_nr_ordem, 
    child_id