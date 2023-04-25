package com.pfa.topsis.controller;

import com.pfa.topsis.Fuzzy.CostFuzzy;
import com.pfa.topsis.Fuzzy.Ftopsis;
import com.pfa.topsis.Fuzzy.MatrixAggregation;
import com.pfa.topsis.Models.*;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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

        getTopsisWithSens(id,0);
        return list3.getBody().size();
    }


    @GetMapping("Topsiscalcule")
    public double[] getTopsisWithSens(@PathParam("id") String id,@PathParam("param") int param){
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

        HttpEntity<String> entity9 = new HttpEntity<>(a);
        ResponseEntity<List<Alternative>> list9=  restTemplate.exchange(
                "http://SERVICE-DONNEES/getalternativebyId?id="+id,
                HttpMethod.GET,
                entity9,
                new ParameterizedTypeReference<List<Alternative>>(){});

        String[] types ={"subtitution-1","subtitution-2","subtitution-3","subtitution-4","subtitution-5","equals","Random"};
        param=0;
        for(int i=0;i<7;i++) {
            matrixagreg=MatrixAggregation.weightedAggregation(cube);
            double[] weiths22 = new double[list3.getBody().size()];
            if (param == 5) {
                for (SousCritere scs : list3.getBody()) {
                    weiths22[(scs.getIndex())] = 1.0 / weiths22.length;
                    System.out.println("weitttttttttttttttttttttttttt   " + (scs.getIndex() + param) % weiths22.length);
                }
            } else if (param == 6) {
                NumberTable table = new NumberTable();
                int[] t = new int[list3.getBody().size()];
                for (int l = 0; l < list3.getBody().size(); l++) {
                    t[l] = l;
                }
                for (SousCritere scs : list3.getBody()) {
                    weiths22[NumberTable.getRandomNumberAndRemove(t)] = scs.getPoids();
                    //  System.out.println("weitttttttttttttttttttttttttt   "+(scs.getIndex() + param)%weiths22.length);
                    t = Arrays.copyOf(t, t.length - 1);
                }


            } else {
                for (SousCritere scs : list3.getBody()) {
                    weiths22[(scs.getIndex() + param) % weiths22.length] = scs.getPoids();
                    System.out.println("weitttttttttttttttttttttttttt   " + (scs.getIndex() + param) % weiths22.length);
                }
            }


            System.out.println(Arrays.toString(weiths22));
            Ftopsis ftopsis = new Ftopsis(matrixagreg, weiths22);

            double[] rank = ftopsis.calcul();


            // Create a new table to store ranks
            int[] rankData = new int[rank.length];

            // Copy input data to a new array for sorting
            double[] sortedData = Arrays.copyOf(rank, rank.length);

            // Sort the input data in ascending order
            Arrays.sort(sortedData);

            // Loop through the input data and assign ranks
            for (int ii = 0; ii < rank.length; ii++) {
                double value = rank[ii];
                int rank1 = Arrays.binarySearch(sortedData, value) + 1;
                rankData[ii] = rank.length-rank1+1;
            }


            List<Alternative> alternativeList = new ArrayList<>();
            for (int ij = 0; ij < rank.length; ij++) {
                System.out.println("saving " + ij);
                list9.getBody().get(ij).setRank(rankData[ij]);
                alternativeList.add(list9.getBody().get(ij));

            }

            AnalyseSens analyseSens = new AnalyseSens(new Projet(id), alternativeList, types[param]);
            HttpEntity<AnalyseSens> entity = new HttpEntity<>(analyseSens);
            ResponseEntity<AnalyseSens> list = restTemplate.exchange(
                    "http://SERVICE-DONNEES/saveAnaluseSens",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<AnalyseSens>() {
                    });

            param++;
            System.out.println("/////////////////////////////////////////////////:");
            System.out.println("final raknkkkkkkkkkkkkkkk");

            System.out.println(Arrays.toString(rank));
            System.out.println(Arrays.toString(rankData));

        }

        return null;
    }

    @GetMapping("Analysecalcule")
    public void getSens(@PathParam("id") String id){

            getTopsisWithSens(id,0);


    }
}
