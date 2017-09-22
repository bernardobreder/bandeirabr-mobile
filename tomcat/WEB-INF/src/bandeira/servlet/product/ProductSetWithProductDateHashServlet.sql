select 
    sum(ora_hash(gapr_cd_id)) parent_id, 
    sum(ora_hash(prod_cd_id)) child_id 
from 
    agregacao_produto 
where 
    agpr_dt_inicio_valid <= ? 
    and (agpr_dt_fim_valid is null or agpr_dt_fim_valid > ?)