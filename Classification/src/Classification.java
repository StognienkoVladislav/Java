
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.*;
import java.lang.String;

/**
 * Created by Twishar on 22.02.2017.
 */

public class Classification {

    public static void main(String[] args) {
        String[] spamMessages = {"Free file for you ","Corporate party tomorrow ","Free file upload "};
        String[] noSpamMessages = {"New meeting tomorrow ","Free sales party ","New greeting text "};

        HashMap<String,Integer> spam = new HashMap<>();
        HashMap<String,Integer> noSpam = new HashMap<>();
        HashMap<String,Integer> allMessages = new HashMap<>();

        String spamWords = "";
        for(int i=0;i<spamMessages.length;i++){
            spamWords+=spamMessages[i]; //Запись СПАМ предложений
        }

        spamWords.toLowerCase();

        String noSpamWords = "";
        for(int i=0;i<noSpamMessages.length;i++){//Аналогично НЕСПАМ
            noSpamWords+=noSpamMessages[i];
        }

        noSpamWords.toLowerCase();

        System.out.println("//////////////////////////  Spam  /////////////////////////");

            String[] allWords = spamWords.split(" ");

            for(int j=0;j<allWords.length ;j++){
                if(spam.containsKey(allWords[j])){
                    spam.put(allWords[j],spam.get(allWords[j])+1);//Запись в Map Спам слово с его кол-во
                }else{
                    spam.put(allWords[j],1);
                }

            }


        for(Map.Entry<String,Integer> entry : spam.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("////////////////////////  NoSpam  /////////////////////////// ");

            allWords = noSpamWords.split(" ");
            for(int j=0;j<allWords.length ;j++){
                if(noSpam.containsKey(allWords[j])){
                    noSpam.put(allWords[j],noSpam.get(allWords[j])+1);//Запись в Map НЕСпам слово с его кол-во
                }else{
                    noSpam.put(allWords[j],1);
                }

            }


        for(Map.Entry<String,Integer> entry : noSpam.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        allMessages.putAll(spam);

        for(int i =0;i<allWords.length;i++){
            if(allMessages.containsKey(allWords[i])){
                allMessages.put(allWords[i],allMessages.get(allWords[i])+1);//Запись в Map НЕСпам слово с его кол-во
            }else{
                allMessages.put(allWords[i],1);
            }
        }

        System.out.println("//////////////////////////  allMessagesCount  /////////////////////////");

        for(Map.Entry<String,Integer> entry : allMessages.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("//////////////////////////  allMessagesReiteration  /////////////////////////");

        HashMap<String,List<Integer>> reiteration = new HashMap<>();

        for(Map.Entry<String,Integer> entry : spam.entrySet()){
            reiteration.put(entry.getKey(),new ArrayList<Integer>());//Записываем из СПАМ массива
            reiteration.get(entry.getKey()).add(entry.getValue());
            reiteration.get(entry.getKey()).add(0);//Значение НЕСПАМ
        }

        for(Map.Entry<String,Integer> entry : noSpam.entrySet()){
            if(reiteration.containsKey(entry.getKey())){
                reiteration.get(entry.getKey()).set(1,entry.getValue());//Аналогично
            }else{
                reiteration.put(entry.getKey(),new ArrayList<Integer>());
                reiteration.get(entry.getKey()).add(0);//Значение СПАМ
                reiteration.get(entry.getKey()).add(entry.getValue());
            }
        }

        for(Map.Entry<String,List<Integer>> entry : reiteration.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue().get(0) + " : " + entry.getValue().get(1));
        }

        System.out.println("//////////////////////////  probability  /////////////////////////");

        HashMap<String,List<Double>> probability = new HashMap<>();

        for(Map.Entry<String,Integer> entry : spam.entrySet()){
            probability.put(entry.getKey(),new ArrayList<Double>());
            probability.get(entry.getKey()).add((double)entry.getValue()/allMessages.get(entry.getKey()));
            probability.get(entry.getKey()).add(0.0);
        }

        for(Map.Entry<String,Integer> entry : noSpam.entrySet()){
            if(probability.containsKey(entry.getKey())){
                probability.get(entry.getKey()).set(1,(double)entry.getValue()/allMessages.get(entry.getKey()));//Аналогично
            }else{
                probability.put(entry.getKey(),new ArrayList<Double>());
                probability.get(entry.getKey()).add(0.0);
                probability.get(entry.getKey()).add((double)entry.getValue()/allMessages.get(entry.getKey()));
            }
        }

        for(Map.Entry<String,List<Double>> entry : probability.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue().get(0) + " : " + entry.getValue().get(1));
        }

        System.out.println("//////////////////////////  probabilityNorm  /////////////////////////");

        HashMap<String,List<Double>> probabilityNorm = new HashMap<>();
        probabilityNorm.putAll(probability);

        for(Map.Entry<String,Integer> entry : allMessages.entrySet()){
            probabilityNorm.get(entry.getKey()).set(0,(((entry.getValue())*(probability.get(entry.getKey()).get(0)) + 0.5)/(entry.getValue()+1)));
            probabilityNorm.get(entry.getKey()).set(1,(((entry.getValue())*(probability.get(entry.getKey()).get(1)) + 0.5)/(entry.getValue()+1)));
        }
/*
        for(Map.Entry<String,Integer> entry : noSpam.entrySet()){
            if(probabilityNorm.containsKey(entry.getKey())){
                probabilityNorm.get(entry.getKey()).set(1,(((entry.getValue())*(probability.get(entry.getKey()).get(1)) + 0.5)/(entry.getValue()+1)));//Аналогично
            }else{
                probabilityNorm.get(entry.getKey()).add(0.0);
                probabilityNorm.get(entry.getKey()).add(((entry.getValue())*(probability.get(entry.getKey()).get(1)) + 0.5)/(entry.getValue()+1));
            }
        }*/

        for(Map.Entry<String,List<Double>> entry : probabilityNorm.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue().get(0) + " : " + entry.getValue().get(1));
        }

        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        String[] strTest = s.split(" ");

        HashMap<String,List<Double>> mapTest = new HashMap<>();

        for(int i=0 ; i<strTest.length;i++){
            for(Map.Entry<String,List<Double>> entry : probabilityNorm.entrySet()) {
                if(strTest[i].equals(entry.getKey())) {
                    mapTest.put(strTest[i], new ArrayList<Double>());
                    mapTest.get(entry.getKey()).add((entry.getValue().get(0)));
                    mapTest.get(entry.getKey()).add((entry.getValue().get(1)));
                }
            }
        }

        System.out.println("//////////////////////////  p_spam $$ p_no_spam  /////////////////////////");

        Double p_spam=0.5,p_no_spam=0.5;

        for(int i=0;i<strTest.length;i++){
            p_spam = p_spam * mapTest.get(strTest[i]).get(0);
            p_no_spam = p_no_spam * mapTest.get(strTest[i]).get(1);
        }

        System.out.println("p_spam : " + p_spam);
        System.out.println("p_no_spam : " + p_no_spam);




        /*List allWords= new ArrayList();//Все слова в предложениях

        for (int i = 0; i < spamMessages.size(); i++) {
            String[] allWordsB=((String)spamMessages.get(i)).split(" ");//Записываем слова из СПАМ сообщений
            for(int j=0;j<allWordsB.length;j++){
                allWords.add(allWordsB[j].toLowerCase());
            }
        }

        for (int i = 0; i < noSpamMessages.size(); i++) {
            String[] allWordsB=((String)noSpamMessages.get(i)).split(" ");//Аналогично с НЕ СПАМ-ом
            for(int j=0;j<allWordsB.length;j++){
                allWords.add(allWordsB[j].toLowerCase());
            }
        }

        for(int i=0;i<allWords.size();i++){
            System.out.println(allWords.get(i));
        }

        Collections.sort(allWords);//Сортируем слова в порядке возрост

        System.out.println("/////////////////////////////////////////////////////////");

        for(int i=0;i<allWords.size();i++){
            System.out.println(allWords.get(i));
        }

        HashMap<String,Integer> reiteration = new HashMap<>();

        int counter = 0;

        for(int i=0 ; i<allWords.size();i++){
            for(int j=0;j<allWords.size();j++){
                if(allWords.get(i).equals(allWords.get(j))){//Кол-во повторений слова
                    counter++;
                }
            }
            reiteration.put(allWords.get(i).toString(),counter);
            counter=0;
        }

        System.out.println("////////////////////////////////////////////////////////////");



        for(Map.Entry<String,Integer> entry:reiteration.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/



    }
}
