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
        String[] aminoacid = {"Arg", "Asn", "Asp", "Cys", "Gln", "Glu", "Gly", "His", "Ile", "Leu", "Lys", "Met", "Phe", "Pro", "Ser", "Thr", "Trp", "Tyr", "Val", "Aib", "NH2", "Mpr", "Cit", "Ala", "Ac"};
        String[] fullname = {"Arginine", "Asparagine", "Aspartate", "Cysteine", "Glutamine", "Glutamate", "Glycine", "Histidine", "Isoleucine", "Leucine", "Lysine", "Methionine", "Phenylalanine", "Proline", "Serine", "Threonine", "Tryptophan", "Tyrosine", "Valine", "Fmoc-Aib-OH", "Amide", "(Trt)Mpr", "Fmoc-Cit-OH", "Alanine", "Ac-"};
        double[] mwwithprotection = {648.78, 596.68, 411.45, 585.72, 610.71, 425.48, 297.31, 619.72, 353.42, 353.42, 468.55, 371.45, 387.44, 337.38, 383.44, 397.47, 526.59, 459.54, 339.39, 325.37, -0.99, 348.46, 397.42, 311.34, 60.05};
        double[] mwwithoutprotection = {156.19, 114.10, 115.09, 103.14, 128.13, 129.12, 57.05, 137.14, 113.16, 113.16, 128.18, 131.19, 147.18, 97.12, 87.08, 101.11, 186.21, 163.18, 99.13, 85.11, -0.99, 88.12, 157.17, 71.08, 42.03};
        double[] pi = {10.8, 5.4, 2.8, 5.1, 5.7, 3.2, 6, 7.6, 6, 6, 9.7, 5.7, 5.5, 6.3, 5.7, 5.6, 5.9, 5.7, 6, 6, 0.4, 5, 6, 6, 0};
        double[] unitprice = {0.5, 0.2, 0.6, 0.2, 0.5, 0.2, 0.2, 0.8, 0.2, 0.2, 0.2, 0.3, 0.2, 0.2, 0.4, 0.2, 0.1, 0.2, 0.2, 1, 0, 0.5, 1, 0.2, 0};
        double[] difficulty = {0.46, 0.97, 0.63, 1.09, 0.79, 1.1, 0.81, 0.64, 1.58, 1.2, 1.31, 1.15, 1.07, 0.26, 0.69, 1.15, 1.01, 1.01, 1.01, 1.2, 0, 0.6, 1, 1.34, 0.1};
        int[] numc = {6, 4, 4, 3, 5, 5, 2, 6, 6, 6, 6, 5, 9, 5, 3, 4, 11, 9, 5, 4, 0, 3, 6, 3, 2};
        int[] numh = {14, 8, 7, 7, 10, 9, 5, 9, 13, 13, 14, 11, 11, 9, 7, 9, 12, 11, 11, 9, 1, 6, 13, 7, 4};
        int[] numn = {4, 2, 1, 1, 2, 1, 1, 3, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 3, 1, 0};      
        int[] numo = {2, 3, 4, 2, 3, 4, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 2, 3, 2, 2, -1, 2, 3, 2, 2};
        int[] nums = {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
        String[] solubility = {"B","N","A","H","N","A","N","N","H","H","B","H","H","N","N","N","H","H","H","N","N","H","N","N","N"};
        for (int i = 0; i < aminoacid.length; i++) {
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




