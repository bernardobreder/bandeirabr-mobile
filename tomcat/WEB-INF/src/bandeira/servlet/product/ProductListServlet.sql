select 
    prod_cd_id id, 
    trim(prod_nm_abreviado) shortname, 
    trim(prod_nm_produto) name, 
    unma_cd_id_meco unit,
    prod_md_dens_padr density, 
    sire_cd_id active, 
    trim(clpd_cd_id) key1, 
    trim(clsp_cd_id) key2, 
    trim(cltp_cd_id) key3, 
    trim(clqp_cd_cl_quaternaria) key4 
from 
    produto
order by
    prod_cd_id