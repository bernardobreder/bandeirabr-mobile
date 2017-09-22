select 
    sum(ora_hash(visa_cd_id)) id,
    sum(ora_hash(gapr_cd_id)) productset_id,
    sum(ora_hash(regi_cd_id)) pointset_id,
    sum(ora_hash(trim(visa_nm_visao))) name,
    sum(ora_hash(visa_nr_fator_divisao)) factor,
    sum(ora_hash(visa_dt_criacao)) created_date,
    sum(ora_hash(trim(user_id_criacao))) user_created_name,
    sum(ora_hash(visa_in_corporativa)) is_view_corp,
    sum(ora_hash(visa_in_publica)) is_view_public
from 
    visao