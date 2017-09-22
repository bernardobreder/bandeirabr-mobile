select 
    visa_cd_id id,
    gapr_cd_id productset_id,
    regi_cd_id pointset_id,
    trim(visa_nm_visao) name,
    visa_nr_fator_divisao factor,
    visa_dt_criacao created_date,
    trim(user_id_criacao) user_created_name,
    case when visa_in_corporativa = 'S' then 1 else 0 end is_view_corp,
    case when visa_in_publica = 'S' then 1 else 0 end is_view_public
from 
    visao