select
    gapr_cd_id parent_id, 
    trim(clpd_cd_id) key1, 
    trim(clsp_cd_id) key2, 
    trim(cltp_cd_id) key3, 
    trim(clqp_cd_cl_quaternaria) key4, 
    agcp_dt_inicio_valid begin_date, 
    agcp_dt_fim_valid end_date 
from 
    agregacao_classe_produto
where 
    agcp_dt_inicio_valid <= ? 
    and (agcp_dt_fim_valid is null or agcp_dt_fim_valid > ?)
order by 
    parent_id,
    key1,
    key2,
    key3,
    key4,
    begin_date