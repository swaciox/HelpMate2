package com.example.verunex.helpmate;

import java.util.Random;

/**
 * Created by Verunex on 2016-11-20.
 */

public class RandomValues {

    Random mRandom = new Random();

    public String randomName(){
        int names_random = mRandom.nextInt(7);
        String[] names = {"Sylwester", "Michal", "Przemysław", "Stefan", "Sebastian", "Rafał", "Jan", "Krzysztof"};
        return names[names_random];
    }

    public String randomNumber(){
        int number_random = mRandom.nextInt(19);
        String[] numbers = {"721821728", "890281921", "601812981", "601912811", "568919031", "762181928", "912748294",
                "504182849", "502857218", "638930192", "543818491", "534386949", "848283019", "726127646", "723182748",
                "912928483", "510204829", "721912918", "504128981", "601298319"};
        return numbers[number_random];
    }

    public String randomCategory(){
        int number_category = mRandom.nextInt(7);
        String[] categories = {"opieka","ogrodnictwo", "pomoc_domowa","hydraulika","remonty","elektryka","pomoc_naukowa","naprawa_urzadzen"};
        return categories[number_category];
    }
    public String randomImage(){
        int number_image = mRandom.nextInt(11);
        String[] images = {"https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg1.png?alt=media&token=bc49c759-a858-4ba7-a378-00bdf0556ad7",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg10.png?alt=media&token=0bce4afa-ba47-457b-b06a-92a47100fb51",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg11.png?alt=media&token=c6c14930-f755-46c0-9eee-2b56c1d5d4ad",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg12.png?alt=media&token=7e0625aa-462c-4bd9-8903-dc081f102a32",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg2.png?alt=media&token=d671a9d5-cbf1-4342-806f-adec73f51d1c",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg3.png?alt=media&token=105b6696-3fdf-45ac-a0ba-7537f454c8ff",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg4.png?alt=media&token=f196a69b-49ab-4492-a685-1bfff9f2be4f",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg5.png?alt=media&token=99e89536-7215-4b91-83dc-efe9bcb43602",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg6.png?alt=media&token=6d39d717-e65c-4544-b749-55b8030b75f9",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg7.png?alt=media&token=7e1384bb-ae0a-464a-bba0-2525389af7e0",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg8.png?alt=media&token=ce0d63d1-560e-48de-bf00-9da39e3733b8",
                "https://firebasestorage.googleapis.com/v0/b/helpmate-b2e14.appspot.com/o/image%2Fimg9.png?alt=media&token=9a4f92f8-662c-4c12-8c15-224410c07b30"};
        return images[number_image];
    }

    public String randomRating(){
        int number_raiting = mRandom.nextInt(6);

        String [] rates = {"2.0f", "1.0f", "2.5f", "4.3f", "4.8f", "3.5f", "3.0f"};
        return rates[number_raiting];
    }

    public String subcategoriesDes(int n){

        String [] subCategories = {
                "naprawa wycieków",
                "wymiana armatury",
                "instalacje elektryczne",
                "naprawa awaryjna",
                "sprzątanie",
                "prasowanie",
                "mycie okien",
                "opieka do dzieci",
                "opieka do osób starszych",
                "opieka dzieci i osób niepełnosprawnych",
                "wyprowadzanie zwierząt",
                "korepetycje",
                "koszenie trawy",
                "prace porządkowe",
                "pielęgnacja ogrodu",
                "naprawa drobnego AGD",
                "naprawa AGD",
                "naprawa RTV",
                "naprawa komputerów/laptopów",
                "malowanie",
                "tapetowanie",
                "kładzenie kafelek",
                "kładzenie paneli podłogowych"
        };

        return subCategories[n];
    }

}

