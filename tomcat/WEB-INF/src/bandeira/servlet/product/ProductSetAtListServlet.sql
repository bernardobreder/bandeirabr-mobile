select 
    gapr_cd_id id, 
    trim(gapr_nm_abreviado) shortname, 
    trim(gapr_nm_completo) name, 
    unma_cd_id unit, 
    gapr_dt_inicio_valid begin_date, 
    gapr_dt_fim_valid end_date 
from 
    grupo_agregacao_produto t 
where 
    t.gapr_in_interna = 'N'
    and gapr_dt_inicio_valid <= ? 
    and (gapr_dt_fim_valid is null or gapr_dt_fim_valid > ?) 
order by 
    gapr_cd_id