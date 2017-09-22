select 
  sum(ora_hash(regi_cd_id)) parent_id, 
  sum(ora_hash(pont_cd_id)) child_id 
from 
  regiao_ponto
where 
  repo_dt_inicio_valid <= ? 
  and (repo_dt_fim_valid is null or repo_dt_fim_valid > ?)