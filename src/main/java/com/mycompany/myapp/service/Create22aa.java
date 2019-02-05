package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Aas;
import com.mycompany.myapp.repository.AasRepository;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class Create22aa {
    private final AasRepository aasRepository;

    public Create22aa(AasRepository aasRepository) {
        this.aasRepository = aasRepository;
    }

    @Transactional
    public void createCalculateEntry() {
        String[] aminoacid = {"Ala","Arg","Asn","Asp","Cys","Gln","Glu","Gly","His","Ile","Leu","Lys","Met","Phe","Pro","Ser","Thr","Trp","Tyr","Val"};
        String[] fullname = {"Alanine","Arginine","Asparagine","Aspartate","Cysteine","Glutamine","Glutamate","Glycine","Histidine","Isoleucine","Leucine","Lysine","Methionine","Phenylalanine","Proline","Serine","Threonine","Tryptophan","Tyrosine","Valine"};
        double[] mwwithprotection = {311.34,648.78,596.68,411.45,585.72,610.71,425.48,297.31,619.72,353.42,353.42,468.55,371.45,387.44,337.38,383.44,397.47,526.59,459.54,339.39};
        double[] mwwithoutprotection = {71.08,156.19,114.10,115.09,103.14,128.13,129.12,57.05,137.14,113.16,113.16,128.18,131.19,147.18,97.12,87.08,101.11,186.21,163.18,99.13};
        double[] pi = {6,10.8,5.4,2.8,5.1,5.7,3.2,6,7.6,6,6,9.7,5.7,5.5,6.3,5.7,5.6,5.9,5.7,6};
        double[] unitprice = {0.2,0.5,0.2,0.6,0.2,0.5,0.2,0.2,0.8,0.2,0.2,0.2,0.3,0.2,0.2,0.4,0.2,0.1,0.2,0.2};
        double[] difficulty = {1.34,0.46,0.97,0.63,1.09,0.79,1.1,0.81,0.64,1.58,1.2,1.31,1.15,1.07,0.26,0.69,1.15,1.01,1.01,1.01};
        int[] numc = {3,6,4,4,3,5,5,2,6,6,6,6,5,9,5,3,4,11,9,5};
        int[] numh = {7,14,8,7,7,10,9,5,9,13,13,14,11,11,9,7,9,12,11,11};
        int[] numn = {1,4,2,1,1,2,1,1,3,1,1,2,1,1,1,1,1,2,1,1};      
        int[] numo = {2,2,3,4,2,3,4,2,2,2,2,2,2,2,2,3,3,2,3,2};
        int[] nums = {0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0};
        String[] solubility = {"H","B","H","A","H","H","A","H","B","H","H","B","H","H","H","H","H","H","H","H"};
        for (int i = 0; i < 20; i++) {
            Aas aaentry = new Aas();
            aaentry.setAaname(fullname[i]);
            aaentry.setThreeletter(aminoacid[i]);
            aaentry.setMwwithprotection(mwwithprotection[i]);
            aaentry.setMwwithoutprotection(mwwithoutprotection[i]);
            aaentry.setPi(pi[i]);
            aaentry.setUnitprice(unitprice[i]);
            aaentry.setDifficulty(difficulty[i]);
            aaentry.setNumc(numc[i]);
            aaentry.setNumh(numh[i]);
            aaentry.setNumn(numn[i]);
            aaentry.setNumo(numo[i]);
            aaentry.setNums(nums[i]);
            aaentry.setSolubility(solubility[i]);
            aasRepository.save(aaentry);
        }
    } 
}




