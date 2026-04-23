import java.util.HashMap;

class libblackjack{
    public static int score(String hand) {
        HashMap<String, Integer> scores = new HashMap<String, Integer>();
        scores.put("2", 2);
        scores.put("3", 3);
        scores.put("4", 4);
        scores.put("5", 5);
        scores.put("6", 6);
        scores.put("7", 7);
        scores.put("8", 8);
        scores.put("9", 9);
        scores.put("10", 10);
        scores.put("J", 10);
        scores.put("Q", 10);
        scores.put("K", 10);
        scores.put("A", 1);

        int num_aces = 0;
        int score = 0;
        String[] split_hand = hand.split(",");
        for (String card : split_hand) {
            if (card.equals("A")) {
                num_aces += 1;
            } else {
                score += scores.get(card);
            }
        }
        
        if (((num_aces - 1 + 11 + score) < 21) & (num_aces > 0)){
            score += num_aces - 1 + 11;
        }
        else{
            score += num_aces;
        }

        return score;
    }
}