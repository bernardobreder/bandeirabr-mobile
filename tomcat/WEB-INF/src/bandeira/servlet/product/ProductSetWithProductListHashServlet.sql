select 
    sum(ora_hash(gapr_cd_id)) parent_id, 
    sum(ora_hash(prod_cd_id)) child_id, 
    sum(ora_hash(agpr_dt_inicio_valid)) begin_date, 
    sum(ora_hash(agpr_dt_fim_valid)) end_date 
from 
    agregacao_produto