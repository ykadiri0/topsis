package com.pfa.topsis.controller;

import com.pfa.topsis.Fuzzy.CostFuzzy;
import com.pfa.topsis.Fuzzy.Ftopsis;
import com.pfa.topsis.Fuzzy.MatrixAggregation;
import com.pfa.topsis.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TopsisController {

    @Autowired
    private RestTemplate restTemplate;
    private double[] weiths;

    @GetMapping("Topsiscalcule/{id}")
    public int getTopsis(@PathVariable("id") String id){
        String a="";
        HttpEntity<String> entity1 = new HttpEntity<>(a);
        ResponseEntity<List<Affectation>> list1=  restTemplate.exchange(
                "http://SERVICE-DONNEES/getaffectationbyProject?id="+id,
                HttpMethod.GET,
                entity1,
                new ParameterizedTypeReference<List<Affectation>>(){});
        HttpEntity<String> entity2 = new HttpEntity<>(a);
        ResponseEntity<List<Projet>> list2=  restTemplate.exchange(
                "http://SERVICE-DONNEES/getprojets?id="+id,
                HttpMethod.GET,
                entity2,
                new ParameterizedTypeReference<List<Projet>>(){});
        HttpEntity<String> entity3 = new HttpEntity<>(a);
        ResponseEntity<List<SousCritere>> list3=  restTemplate.exchange(
                "http://SERVICE-DONNEES/getSousCriterebyprojet?id="+id,
                HttpMethod.GET,
                entity3,
                new ParameterizedTypeReference<List<SousCritere>>(){});

        CostFuzzy[][][] cube=new CostFuzzy[list1.getBody().size()][Integer.parseInt(list2.getBody().get(0).getNumAlternative())][list3.getBody().size()];

        for(int i=0;i<list1.getBody().size();i++){
            System.out.println("user  "+i);
            ResponseEntity<List<SousCritereAlternative>> list4=  restTemplate.exchange(
                    "http://SERVICE-DONNEES/getSAbyUser?id="+list1.getBody().get(i).getUsers().getId(),
                    HttpMethod.GET,
                    entity3,
                    new ParameterizedTypeReference<List<SousCritereAlternative>>(){});
            CostFuzzy[][] matrix=new CostFuzzy[Integer.parseInt(list2.getBody().get(0).getNumAlternative())][list3.getBody().size()];
            System.out.println("sizeeeeeeeeeee  " +list4.getBody().size());
            for(SousCritereAlternative sca :list4.getBody()){
                System.out.println(sca.getId() +"         " +sca.getSousCritere().getId()+"   "+sca.getSousCritere().getGbindex()+"          " +sca.getAlternative().getIndex()+"    p="+ sca.getCostFuzzy());
                matrix[sca.getAlternative().getIndex()][sca.getSousCritere().getGbindex()]=sca.getCostFuzzy();
            }
            cube[i]=matrix;
        }

        CostFuzzy[][] matrixagreg=MatrixAggregation.weightedAggregation(cube);
        weiths=new double[list3.getBody().size()];
        for(SousCritere scs :list3.getBody()){
            weiths[scs.getIndex()]=scs.getPoids();

        }
        Ftopsis ftopsis=new Ftopsis(matrixagreg,weiths);
        double[] rank=ftopsis.calcul();



        HttpEntity<String> entity9 = new HttpEntity<>(a);
        ResponseEntity<List<Alternative>> list9=  restTemplate.exchange(
                "http://SERVICE-DONNEES/getalternativebyId?id="+id,
                HttpMethod.GET,
                entity9,
                new ParameterizedTypeReference<List<Alternative>>(){});


        for(int i=0;i<rank.length;i++){
            System.out.println("saving "+i);
                list9.getBody().get(i).setRank(rank[i]);
                HttpEntity<Alternative> entity = new HttpEntity<>(list9.getBody().get(i));
                ResponseEntity<Alternative> list=  restTemplate.exchange(
                        "http://SERVICE-DONNEES/savealternative",
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<Alternative>(){});



        }


        return list3.getBody().size();
    }
}
