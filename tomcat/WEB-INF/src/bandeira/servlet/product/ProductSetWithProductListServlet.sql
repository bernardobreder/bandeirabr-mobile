select 
    gapr_cd_id parent_id, 
    prod_cd_id child_id, 
    agpr_dt_inicio_valid begin_date, 
    agpr_dt_fim_valid end_date 
from 
    agregacao_produto 
order by 
    parent_id, 
    agpr_nr_ordem,
    child_id,
    agpr_dt_inicio_valid