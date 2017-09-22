select 
    time, 
    balance, 
    point, 
    product, 
    trunc(corp_mass) corp_mass, 
    trunc(corp_volume) corp_volume, 
    trunc(mass) mass, 
    trunc(volume) volume 
from 
    table(scenarium_pkg.scenarium_balance(?))
where
	mass != 0 or volume != 0
order by
    time, balance, point, product