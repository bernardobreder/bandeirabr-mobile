select 
    sum(ora_hash(prod_cd_id)) id, 
    sum(ora_hash(trim(prod_nm_abreviado))) shortname, 
    sum(ora_hash(trim(prod_nm_produto))) name, 
    sum(ora_hash(unma_cd_id_meco)) unit,
    sum(ora_hash(prod_md_dens_padr)) density, 
    sum(ora_hash(sire_cd_id)) active, 
    sum(ora_hash(trim(clpd_cd_id))) key1, 
    sum(ora_hash(trim(clsp_cd_id))) key2, 
    sum(ora_hash(trim(cltp_cd_id))) key3, 
    sum(ora_hash(trim(clqp_cd_cl_quaternaria))) key4 
from 
    produto