select 
    regi_cd_id id,
    regi_sg_regiao shortname,
    regi_nm_regiao name,
    regi_dt_inicio_valid begin_date,
    regi_dt_fim_valid end_date
from 
    regiao
where 
    regi_in_interna = 'N'
    and regi_dt_inicio_valid <= ? 
    and (regi_dt_fim_valid is null or regi_dt_fim_valid > ?)