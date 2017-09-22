select 
    sum(ora_hash(gapr_cd_id)) id, 
    sum(ora_hash(trim(gapr_nm_abreviado))) shortname, 
    sum(ora_hash(trim(gapr_nm_completo))) name, 
    sum(ora_hash(unma_cd_id)) unit, 
    sum(ora_hash(gapr_dt_inicio_valid)) begin_date, 
    sum(ora_hash(gapr_dt_fim_valid)) end_date 
from 
    grupo_agregacao_produto t 
where 
    t.gapr_in_interna = 'N'