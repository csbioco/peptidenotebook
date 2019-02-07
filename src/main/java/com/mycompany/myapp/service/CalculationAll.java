package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Aas;
import com.mycompany.myapp.repository.AasRepository;
import com.mycompany.myapp.domain.Calculated;
import com.mycompany.myapp.repository.CalculatedRepository;
import com.mycompany.myapp.domain.CalculatedEntry;
import com.mycompany.myapp.repository.CalculatedEntryRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.domain.Protocolentry;
import com.mycompany.myapp.repository.ProtocolentryRepository;
import com.mycompany.myapp.service.classlib.Ret;
import com.mycompany.myapp.service.classlib.Protocolret;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class CalculationAll {
    private final CalculatedRepository calculatedRepository;
    private final CalculatedEntryRepository calculatedEntryRepository;
    private final AasRepository aasRepository;
    private final UserService userService;
    private final ProtocolentryRepository protocolentryRepository;
    public CalculationAll(CalculatedRepository calculatedRepository, CalculatedEntryRepository calculatedEntryRepository,
                          AasRepository aasRepository, UserService userService, ProtocolentryRepository protocolentryRepository) {
        this.calculatedRepository = calculatedRepository;
        this.calculatedEntryRepository = calculatedEntryRepository;
        this.aasRepository = aasRepository;
        this.userService = userService;
        this.protocolentryRepository = protocolentryRepository;
    }

    @Transactional
    public void createCalculateEntry(Calculated inputcal) {
        int sc = inputcal.getSc();
        int dc = inputcal.getDc();
        int scale = inputcal.getScale();
        String[] inputSequence = inputcal.getSequence().trim().split("-");
        CalculatedEntry[] createCalculatedEntries = new CalculatedEntry[inputSequence.length];
        for (int i = inputSequence.length - 1; i >= 0; i--) {
            createCalculatedEntries[i] = new CalculatedEntry();
            createCalculatedEntries[i].setAa(inputSequence[i]);
            createCalculatedEntries[i].setSc(sc);
            createCalculatedEntries[i].setDc(dc);
            createCalculatedEntries[i].setCalculated(inputcal);
            createCalculatedEntries[i].setSequencenumber((i + 1));
            createCalculatedEntries[i].setScale(scale);
            //getProtocolname
            createCalculatedEntries[i].setProtocolname(inputcal.getProtocolname());

            calculatedEntryRepository.save(createCalculatedEntries[i]);
        }
    }
    
    
    @Transactional
    public boolean checksequence(Calculated inputcal) {
        List<Aas> allAA = aasRepository.findAll();
        HashSet<String> nameSet = new HashSet<>();
        for (Aas aa : allAA) {
            nameSet.add(aa.getThreeletter());
        }
        int numcys = 0;
        String[] inputSequence = inputcal.getSequence().trim().split("-");
        for (String str : inputSequence) {
            if (!nameSet.contains(str)) {
                return false;
            }
            if (str.equals("Cys")) {
                numcys++;
            }
        }
        if (inputcal.getBound() != 0 && inputcal.getBound() >= numcys) {
            return false;
        }
        return true;
    }

    HashMap<String, Double[]> protocolmap;
    @Transactional
    public void calculateresult(Long id) {
        List<CalculatedEntry> entry = calculatedEntryRepository.findByCalculateId(id);
        Calculated calculatedata = calculatedRepository.findByCalculateId(id);
        protocolmap = new HashMap<>();
        //sort calculated entry
        Collections.sort(entry, (CalculatedEntry c1, CalculatedEntry c2) -> {
            return c2.getSequencenumber().compareTo(c1.getSequencenumber());
        });

        //put all aa to hashmap
        List<Aas> allAA = aasRepository.findAll();
        HashMap<String, Aas> positionMap = new HashMap<>();
        for (Aas aa : allAA) {
            positionMap.put(aa.getThreeletter(), aa);
        }
        int numc = 0, numh = 0, numn = 0, numo = 0, nums = 0;
        double pi = calculatepi(entry, positionMap);

        double totreagentcost = 0.0;
        double mwwithprotection = 18 - 2 * calculatedata.getBound() * 1.01;
        double mwwithoutprotection = 18 - 2 * calculatedata.getBound() * 1.01;
        int basic = 0, acid = 0, hydro = 0;
        double costresin = 0, weightgain = 0, totalweight = 0, costaa = 0, sumeachaaweight = 0;
        double netcharge = 0;
        double startresin = calculatedata.getScale() / calculatedata.getSubstitude();
        for (int i = 0; i < entry.size(); i++) {
            //difficulty
            if (entry.size() < 4) {
                entry.get(i).setDifficulty(0.88);
            }
            else {
                if (i < 4) {
                    entry.get(i).setDifficulty(0.88);
                }
                else {
                    double tmpdif = 0;
                    for (int j = 1; j <= 4; j++) {
                        tmpdif += positionMap.get(entry.get(i - j).getAa()).getDifficulty();
                    }
                    entry.get(i).setDifficulty(Math.round(tmpdif / 4 * 100.0) / 100.0);
                }
            }
   
            if (entry.get(i).getProtocolname() != null) {
                List<Protocolentry> curprotocol = protocolentryRepository.findByProtocolId(Long.parseLong(entry.get(i).getProtocolname()));
                for (Protocolentry p : curprotocol) {
                    if (!protocolmap.containsKey(p.getReagent().getReagentname())) {
                        protocolmap.put(p.getReagent().getReagentname(), new Double[]{0.0,0.0,0.0});
                    }
                    protocolmap.get(p.getReagent().getReagentname())[0] += (p.getSensor().getAmount());
                    protocolmap.get(p.getReagent().getReagentname())[1] += (p.getSensor().getAmount() * p.getReagent().getUnitprice());
                    protocolmap.get(p.getReagent().getReagentname())[2] += (p.getSensor().getAmount() * p.getReagent().getWasterunitprice());
                    totreagentcost += (p.getSensor().getAmount() * p.getReagent().getUnitprice());
                    totreagentcost += (p.getSensor().getAmount() * p.getReagent().getWasterunitprice());
                }
            }


            // int basic = 0, acid = 0, hydro = 0;
            if (positionMap.get(entry.get(i).getAa()).getSolubility().equals("H")) {
                hydro++;
            }
            else if (positionMap.get(entry.get(i).getAa()).getSolubility().equals("B")) {
                basic++;
            }
            else if (positionMap.get(entry.get(i).getAa()).getSolubility().equals("A")) {
                acid++;
            }

            // double mwwithprotection = 18;
            // double mwwithoutprotection = 18;         
            //mw and total mw
            mwwithprotection += positionMap.get(entry.get(i).getAa()).getMwwithprotection();
            mwwithoutprotection  += positionMap.get(entry.get(i).getAa()).getMwwithoutprotection();
            entry.get(i).setMwwithprotection(Math.round(mwwithprotection * 100.0) / 100.0);
            entry.get(i).setMwwithoutprotection(Math.round(mwwithoutprotection * 100.0) / 100.0);
            double tmpeachaaweight = (entry.get(i).getSc() + entry.get(i).getDc()) * entry.get(i).getScale() * positionMap.get(entry.get(i).getAa()).getMwwithprotection();
            sumeachaaweight += tmpeachaaweight;            
            entry.get(i).setEachaaweight(Math.round(tmpeachaaweight * 100.0) / 100.0);
            double currentresinweight = startresin + mwwithprotection * entry.get(i).getScale();
            entry.get(i).setCurrentresinweight(Math.round(currentresinweight * 100.0) / 100.0);            
            
            //number of c, h, n, o, s;
            numc += positionMap.get(entry.get(i).getAa()).getNumc();
            numh += positionMap.get(entry.get(i).getAa()).getNumh();
            numn += positionMap.get(entry.get(i).getAa()).getNumn();
            numo += positionMap.get(entry.get(i).getAa()).getNumo();
            nums += positionMap.get(entry.get(i).getAa()).getNums();


            //cost of aa
            costaa += tmpeachaaweight * positionMap.get(entry.get(i).getAa()).getUnitprice();

            //get netcharge
            String aaname = entry.get(i).getAa();
            if (aaname.equals("Asp") || aaname.equals("Glu")) {
                netcharge -= 1; 
            }
            else if (aaname.equals("His")) {
                netcharge += 0.5;
            }
            else if (aaname.equals("Arg") || aaname.equals("Lys")) {
                netcharge += 1;
            }
        }
        int numho = entry.size() - 1;
        calculatedata.setNetcharge(netcharge);
        calculatedata.setPi(Math.round(pi * 100.0) / 100.0);
        calculatedata.setNumc(numc);
        calculatedata.setNumn(numn);
        calculatedata.setNumh(numh - 2*numho);
        calculatedata.setNumo(numo - numho);
        calculatedata.setNums(nums);
        calculatedata.setStartresin(startresin);
        calculatedata.setCostresin(Math.round(startresin * calculatedata.getResinunitprice() * 100.0) / 100.0);
        calculatedata.setWeightgain(Math.round(calculatedata.getScale() * entry.get(entry.size() - 1).getMwwithprotection() / 1000 * 100.0) / 100.0);
        calculatedata.setTotalweight(Math.round((calculatedata.getWeightgain() + startresin) * 100.0) / 100.0);
        calculatedata.setCostaa(Math.round(costaa * 100.0) / 100.0);
        calculatedata.setSumeachaaweight(Math.round(sumeachaaweight * 100.0) / 100.0); 
        calculatedata.setUser(userService.getUserWithAuthorities().get());
        calculatedata.setSolubility(basic + "B" + acid + "A" + hydro + "H");
        calculatedata.setCostwaste(Math.round(totreagentcost * 100.0) / 100.0);
        calculatedRepository.save(calculatedata);
    }

    private double calculatepi(List<CalculatedEntry> entry, HashMap<String, Aas> positionMap) {
        double pre = 0;
        double post = 14;
        double total_charge = 1000000;
        Double current_pi = 0.0;
        while (Math.abs(total_charge) > 0.0000000001)
        {
            current_pi = (pre + post) / 2;
            double current_temp = Math.pow(10, current_pi);
            double base_netcharge = Math.pow(10, 9.69) / (Math.pow(10, 9.69) + current_temp);
            double acid_netcharge = current_temp / (Math.pow(10, 2.34) + current_temp);
            for (int i = 0; i < entry.size(); i++)
            {   
                String tmp = entry.get(i).getAa();
                Aas tmpaa = positionMap.get(tmp);
                if (tmp.equals("Arg") || tmp.equals("Lys") || tmp.equals("His")) {
                    base_netcharge += (Math.pow(10, tmpaa.getPi()) / (Math.pow(10, tmpaa.getPi()) + current_temp));
                }
                if (tmp.equals("Asp") || tmp.equals("Glu") || tmp.equals("Cys") || tmp.equals("Tyr")) {
                    acid_netcharge += (current_temp / (Math.pow(10, tmpaa.getPi()) + current_temp));
                }
            }
            total_charge = base_netcharge - acid_netcharge;
            if (total_charge > 0)
            {
                pre = current_pi;
            }
            else
            {
                post = current_pi;
            }
        }
        return current_pi;
    }

    public Ret returncal(Long id) {
        calculateresult(id);
        List<CalculatedEntry> res_cal = calculatedEntryRepository.findByCalculateId(id);
        //protocolmap  HashMap<String, Double[]>
        List<Protocolret> res_pro = new ArrayList<>();
        Double totolcost = 0.0;
        for (String e : protocolmap.keySet()) {
            res_pro.add(new Protocolret(e, protocolmap.get(e)[0], protocolmap.get(e)[1], protocolmap.get(e)[2]));
            totolcost += protocolmap.get(e)[1] + protocolmap.get(e)[2];
        }
        protocolmap.clear();
        return new Ret(res_pro, res_cal);
    }


    public void deleteallreagentsensorprotocol() {
        
    }
}


