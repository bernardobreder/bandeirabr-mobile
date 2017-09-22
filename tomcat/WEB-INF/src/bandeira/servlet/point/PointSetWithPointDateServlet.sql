select 
  regi_cd_id parent_id, 
  pont_cd_id child_id 
from 
  regiao_ponto
where 
  repo_dt_inicio_valid <= ? 
  and (repo_dt_fim_valid is null or repo_dt_fim_valid > ?)
order by 
  regi_cd_id,
  repo_nr_ordem,
  pont_cd_id,
  repo_dt_inicio_valid