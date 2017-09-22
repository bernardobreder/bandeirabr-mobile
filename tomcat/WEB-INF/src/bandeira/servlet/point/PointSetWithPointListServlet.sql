select 
  regi_cd_id parent_id, 
  pont_cd_id child_id, 
  repo_dt_inicio_valid begin_date,
  repo_dt_fim_valid end_date
from 
  regiao_ponto
order by 
  regi_cd_id,
  repo_nr_ordem,
  pont_cd_id,
  repo_dt_inicio_valid