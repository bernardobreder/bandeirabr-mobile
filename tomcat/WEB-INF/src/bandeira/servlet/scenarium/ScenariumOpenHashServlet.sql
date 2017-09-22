select 
  sum(ora_hash(time)) time, 
  sum(ora_hash(balance)) balance, 
  sum(ora_hash(point)) point, 
  sum(ora_hash(product)) product, 
  sum(ora_hash(trunc(corp_mass))) corp_mass, 
  sum(ora_hash(trunc(corp_volume))) corp_volume, 
  sum(ora_hash(trunc(mass))) mass, 
  sum(ora_hash(trunc(volume))) volume
from 
  table(scenarium_pkg.scenarium_balance(?)) 
where
    mass != 0 or volume != 0