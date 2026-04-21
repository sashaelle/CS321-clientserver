hand = "A,A,A,A,10"

def score(hand):
    scores = {
        '2': 2,
        '3': 3,
        '4': 4,
        '5': 5,
        '6': 6,
        '7': 7,
        '8': 8,
        '9': 9,
        '10': 10,
        'J': 10,
        'Q': 10,
        'K': 10
    }

    num_aces = 0;
    score = 0;
    split_hand = hand.rsplit(",");
    for card in split_hand:
        if card == 'A':
            num_aces += 1;
        else:
            score += scores[card]
    
    num_aces_11 = (int)((21-score)/11)
    num_aces_1 = num_aces - num_aces_11

    score += num_aces_1 + num_aces_11*11

    return score

print(score(hand))