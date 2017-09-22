select * from (
  select p.pont_cd_id id, trim(p.tipo_cd_id) type,
  case 
    when p.tipo_cd_id in ('REF', 'TER', 'MAR') then (select trim(o.orga_sg_id) from ponto_orgao po join orgao o on po.orga_cd_cbi = o.orga_cd_cbi where p.pont_cd_id = po.pont_cd_id)
    when p.tipo_cd_id in ('TROL') then (select trim(a.arco_nm_arco) from ponto_oleoduto po join arco a on po.pont_cd_id = a.ponto_cd_id_oleoduto where p.pont_cd_id = po.pont_cd_id)
    when p.tipo_cd_id in ('MUNI') then (select trim(mu.city_name) from adrcity mu join ponto_municipio pm on pm.city_code = mu.city_code where p.pont_cd_id = pm.pont_cd_id)
    when p.tipo_cd_id in ('OUTPROD') then (select trim(op.oupr_nm_nome) from outro_produtor op join ponto_outro_produtor pop on pop.oupr_cd_id = op.oupr_cd_id where p.pont_cd_id = pop.pont_cd_id)
    when p.tipo_cd_id in ('OUTCONS') then (select trim(ci.coin_nm_nome) from consumidor_interno ci join ponto_consumidor_interno pci on pci.coin_cd_id = ci.coin_cd_id where p.pont_cd_id = pci.pont_cd_id)
    when p.tipo_cd_id in ('SHIP', 'TBN') then (trim(p.tipo_cd_id))
    when p.tipo_cd_id in ('NAVIO') then (select trim(emba.emba_nm_completo) from embarcacao emba, ponto_embarcacao poe where p.pont_cd_id = poe.pont_cd_id and poe.emba_cd_id = emba.emba_cd_id)
    when p.tipo_cd_id in ('PPRODBR') then (select trim(p.prod_nm_completo) from ponto_producao_br ppbr join produto p on ppbr.prod_cd_id = p.prod_cd_id where p.pont_cd_id = ppbr.pont_cd_id)
    when p.tipo_cd_id in ('PPRODDERIV') then (select trim(ppd.popd_nm_pprodderiv) from ponto_producao_derivado ppd where p.pont_cd_id = ppd.pont_cd_id)
    when p.tipo_cd_id in ('TTERC') then (select trim(ttt.tete_nm_term_terc) from ponto_term_terceiro ptt inner join term_terceiro ttt on ttt.cent_cd_id_r3 = ptt.cent_cd_id_r3 and ttt.eptt_cd_deposito_produto = ptt.eptt_cd_deposito_produto where ptt.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('TMARTERC') then (select trim(po.poop_nm_completo) from ponto_term_mar_terc ptm inner join ponto_operacional po on po.poop_cd_id = ptm.poop_cd_id where ptm.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('LOCESC') then (select trim(po.poop_nm_completo) from ponto_local_escoamento ple inner join ponto_operacional po on po.poop_cd_id = ple.poop_cd_id where ple.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('FORNECEXT') then (select trim(po.poop_nm_completo) from ponto_fornecedor_exterior pfe inner join ponto_operacional po on po.poop_cd_id = pfe.poop_cd_id where pfe.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('PASSAGEM') then (select trim(pp.nool_cd_id) from ponto_passagem pp where pp.pont_cd_id = p.pont_cd_id)
  else null end name
  from ponto p
) 
where name is not null order by id