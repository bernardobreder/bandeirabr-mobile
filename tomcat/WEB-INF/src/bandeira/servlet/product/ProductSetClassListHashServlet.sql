select
    sum(ora_hash(gapr_cd_id)) parent_id, 
    sum(ora_hash(trim(clpd_cd_id))) key1, 
    sum(ora_hash(trim(clsp_cd_id))) key2, 
    sum(ora_hash(trim(cltp_cd_id))) key3, 
    sum(ora_hash(trim(clqp_cd_cl_quaternaria))) key4, 
    sum(ora_hash(agcp_dt_inicio_valid)) begin_date, 
    sum(ora_hash(agcp_dt_fim_valid)) end_date 
from 
    agregacao_classe_produto