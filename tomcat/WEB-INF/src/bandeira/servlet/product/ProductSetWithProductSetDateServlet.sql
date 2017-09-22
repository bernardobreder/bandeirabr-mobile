select 
    gapr_cd_id_pai parent_id, 
    gapr_cd_id_filho child_id 
from 
    agregacao_agregacao 
where 
    agag_dt_inicio_valid <= ? 
    and (agag_dt_fim_valid is null or agag_dt_fim_valid > ?)
order by 
    parent_id,
    agag_nr_ordem, 
    child_id 