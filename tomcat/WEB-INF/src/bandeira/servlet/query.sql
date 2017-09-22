select gapr_cd_id id, gapr_nm_abreviado shortname, gapr_nm_completo name, unma_cd_id unit, gapr_dt_inicio_valid begin_date, gapr_dt_fim_valid end_date from grupo_agregacao_produto t where t.gapr_in_interna = 'N' order by gapr_cd_id;
select prod_cd_id id, prod_nm_abreviado shortname, prod_nm_produto name, unma_cd_id_meco unit,prod_md_dens_padr density, sire_cd_id active, clpd_cd_id key1, clsp_cd_id key2, cltp_cd_id key3, clqp_cd_cl_quaternaria key4 from produto;
select gapr_cd_id_pai parent, gapr_cd_id_filho child, agag_nr_ordem ordem, agag_dt_inicio_valid begin_date, agag_dt_fim_valid end_date from agregacao_agregacao order by parent, child, ordem;
select gapr_cd_id parent, clpd_cd_id key1, clsp_cd_id key2, cltp_cd_id key3, clqp_cd_cl_quaternaria key4, agcp_dt_inicio_valid begin_date, agcp_dt_fim_valid end_date from agregacao_classe_produto;
select gapr_cd_id parent, prod_cd_id child, agpr_nr_ordem ordem, agpr_dt_inicio_valid begin_date, agpr_dt_fim_valid end_date from agregacao_produto order by parent, child, ordem;

PT_SEA_TERM               constant tipo_ponto.tipo_cd_id%type := 'MAR';
  PT_TERRESTRIAL_TERM       constant tipo_ponto.tipo_cd_id%type := 'TER';
  PT_REFINERY               constant tipo_ponto.tipo_cd_id%type := 'REF';
  PT_PIPELINE               constant tipo_ponto.tipo_cd_id%type := 'TROL';
  PT_OPEN_SHIP              constant tipo_ponto.tipo_cd_id%type := 'NAVIO';
  PT_SHIP                   constant tipo_ponto.tipo_cd_id%type := 'TBN';
  PT_DELIVERY               constant tipo_ponto.tipo_cd_id%type := 'MUNI';
  PT_OTHER_PRODUCERS        constant tipo_ponto.tipo_cd_id%type := 'OUTPROD';
  PT_OTHER_CONSUMERS        constant tipo_ponto.tipo_cd_id%type := 'OUTCONS';
  PT_THIRD_PARTY_WITH_STOCK constant tipo_ponto.tipo_cd_id%type := 'TERCEST'; -- NAO TEM DADOS
  PT_FOREIGN_SUPPLIER       constant tipo_ponto.tipo_cd_id%type := 'FORNECEXT';
  PT_THIRD_PARTY_SEA_TERM   constant tipo_ponto.tipo_cd_id%type := 'TMARTERC';
  PT_THIRD_PARTY_TER_TERM   constant tipo_ponto.tipo_cd_id%type := 'TTERC';
  PT_PPROD_BR               constant tipo_ponto.tipo_cd_id%type := 'PPRODBR';
  PT_FLOW                   constant tipo_ponto.tipo_cd_id%type := 'LOCESC';
  PT_PPRODDERIV             constant tipo_ponto.tipo_cd_id%type := 'PPRODDERIV';
  PT_TRANSIT                constant tipo_ponto.tipo_cd_id%type := 'PASSAGEM';
  
select * from arco where ponto_cd_id_oleoduto = 5834;

select * from (
  select p.pont_cd_id id, p.tipo_cd_id type,
  case 
    when p.tipo_cd_id in ('REF', 'TER', 'MAR') then (select o.orga_sg_id from ponto_orgao po join orgao o on po.orga_cd_cbi = o.orga_cd_cbi where p.pont_cd_id = po.pont_cd_id)
    when p.tipo_cd_id in ('TROL') then (select a.arco_nm_arco from ponto_oleoduto po join arco a on po.pont_cd_id = a.ponto_cd_id_oleoduto where p.pont_cd_id = po.pont_cd_id)
    when p.tipo_cd_id in ('MUNI') then (select mu.city_name from adrcity mu join ponto_municipio pm on pm.city_code = mu.city_code where p.pont_cd_id = pm.pont_cd_id)
    when p.tipo_cd_id in ('OUTPROD') then (select op.oupr_nm_nome from outro_produtor op join ponto_outro_produtor pop on pop.oupr_cd_id = op.oupr_cd_id where p.pont_cd_id = pop.pont_cd_id)
    when p.tipo_cd_id in ('OUTCONS') then (select ci.coin_nm_nome from consumidor_interno ci join ponto_consumidor_interno pci on pci.coin_cd_id = ci.coin_cd_id where p.pont_cd_id = pci.pont_cd_id)
    when p.tipo_cd_id in ('SHIP', 'TBN') then (p.tipo_cd_id)
    when p.tipo_cd_id in ('NAVIO') then (select emba.emba_nm_completo from embarcacao emba, ponto_embarcacao poe where p.pont_cd_id = poe.pont_cd_id and poe.emba_cd_id = emba.emba_cd_id)
    when p.tipo_cd_id in ('PPRODBR') then (select p.prod_nm_completo from ponto_producao_br ppbr join produto p on ppbr.prod_cd_id = p.prod_cd_id where p.pont_cd_id = ppbr.pont_cd_id)
    when p.tipo_cd_id in ('PPRODDERIV') then (select ppd.popd_nm_pprodderiv from ponto_producao_derivado ppd where p.pont_cd_id = ppd.pont_cd_id)
    when p.tipo_cd_id in ('TTERC') then (select ttt.tete_nm_term_terc from ponto_term_terceiro ptt inner join term_terceiro ttt on ttt.CENT_CD_ID_R3 = ptt.cent_cd_id_r3 and ttt.eptt_cd_deposito_produto = ptt.eptt_cd_deposito_produto where ptt.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('TMARTERC') then (select po.poop_nm_completo from ponto_term_mar_terc ptm inner join ponto_operacional po on po.poop_cd_id = ptm.poop_cd_id where ptm.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('LOCESC') then (select po.poop_nm_completo from ponto_local_escoamento ple inner join ponto_operacional po on po.poop_cd_id = ple.poop_cd_id where ple.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('FORNECEXT') then (select po.poop_nm_completo from ponto_fornecedor_exterior pfe inner join ponto_operacional po on po.poop_cd_id = pfe.poop_cd_id where pfe.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('PASSAGEM') then (select pp.nool_cd_id from ponto_passagem pp where pp.pont_cd_id = p.pont_cd_id)
  else null end name
  from ponto p
) where name is not null order by id;

select * from (
  select p.pont_cd_id id, p.tipo_cd_id type,
  case 
    when p.tipo_cd_id in ('REF', 'TER', 'MAR') then (select o.orga_sg_id from ponto_orgao po join orgao o on po.orga_cd_cbi = o.orga_cd_cbi where p.pont_cd_id = po.pont_cd_id)
    when p.tipo_cd_id in ('TROL') then (select a.arco_nm_arco from ponto_oleoduto po join arco a on po.pont_cd_id = a.ponto_cd_id_oleoduto where p.pont_cd_id = po.pont_cd_id)
    when p.tipo_cd_id in ('MUNI') then (select mu.city_name from adrcity mu join ponto_municipio pm on pm.city_code = mu.city_code where p.pont_cd_id = pm.pont_cd_id)
    when p.tipo_cd_id in ('OUTPROD') then (select op.oupr_nm_nome from outro_produtor op join ponto_outro_produtor pop on pop.oupr_cd_id = op.oupr_cd_id where p.pont_cd_id = pop.pont_cd_id)
    when p.tipo_cd_id in ('OUTCONS') then (select ci.coin_nm_nome from consumidor_interno ci join ponto_consumidor_interno pci on pci.coin_cd_id = ci.coin_cd_id where p.pont_cd_id = pci.pont_cd_id)
    when p.tipo_cd_id in ('SHIP', 'TBN') then (p.tipo_cd_id)
    when p.tipo_cd_id in ('NAVIO') then (select emba.emba_nm_completo from embarcacao emba, ponto_embarcacao poe where p.pont_cd_id = poe.pont_cd_id and poe.emba_cd_id = emba.emba_cd_id)
    when p.tipo_cd_id in ('PPRODBR') then (select p.prod_nm_completo from ponto_producao_br ppbr join produto p on ppbr.prod_cd_id = p.prod_cd_id where p.pont_cd_id = ppbr.pont_cd_id)
    when p.tipo_cd_id in ('PPRODDERIV') then (select ppd.popd_nm_pprodderiv from ponto_producao_derivado ppd where p.pont_cd_id = ppd.pont_cd_id)
    when p.tipo_cd_id in ('TTERC') then (select ttt.tete_nm_term_terc from ponto_term_terceiro ptt inner join term_terceiro ttt on ttt.CENT_CD_ID_R3 = ptt.cent_cd_id_r3 and ttt.eptt_cd_deposito_produto = ptt.eptt_cd_deposito_produto where ptt.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('TMARTERC') then (select po.poop_nm_completo from ponto_term_mar_terc ptm inner join ponto_operacional po on po.poop_cd_id = ptm.poop_cd_id where ptm.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('LOCESC') then (select po.poop_nm_completo from ponto_local_escoamento ple inner join ponto_operacional po on po.poop_cd_id = ple.poop_cd_id where ple.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('FORNECEXT') then (select po.poop_nm_completo from ponto_fornecedor_exterior pfe inner join ponto_operacional po on po.poop_cd_id = pfe.poop_cd_id where pfe.pont_cd_id = p.pont_cd_id)
    when p.tipo_cd_id in ('PASSAGEM') then (select pp.nool_cd_id from ponto_passagem pp where pp.pont_cd_id = p.pont_cd_id)
  else '' end name
  from ponto p order by id
) where name is null;