select 
  regi_cd_id_pai parent_id,
  regi_cd_id_filho child_id,
  rere_dt_inicio_valid begin_date,
  rere_dt_fim_valid end_date
from 
  regiao_regiao
where 
  rere_dt_inicio_valid <= ? 
  and (rere_dt_fim_valid is null or rere_dt_fim_valid > ?)
order by 
  regi_cd_id_pai, 
  rere_nr_ordem, 
  regi_cd_id_filho,
  rere_dt_inicio_valid