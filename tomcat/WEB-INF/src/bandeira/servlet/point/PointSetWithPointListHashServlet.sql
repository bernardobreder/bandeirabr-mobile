select 
  sum(ora_hash(regi_cd_id)) parent_id, 
  sum(ora_hash(pont_cd_id)) child_id, 
  sum(ora_hash(repo_dt_inicio_valid)) begin_date,
  sum(ora_hash(repo_dt_fim_valid)) end_date
from 
  regiao_ponto