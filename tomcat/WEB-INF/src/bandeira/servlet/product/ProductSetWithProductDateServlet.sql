select 
    gapr_cd_id parent_id, 
    prod_cd_id child_id 
from 
    agregacao_produto 
where 
    agpr_dt_inicio_valid <= ? 
    and (agpr_dt_fim_valid is null or agpr_dt_fim_valid > ?)
order by 
    parent_id, 
    agpr_nr_ordem,
    child_id,
    agpr_dt_inicio_valid