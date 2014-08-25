package maven.blackjack2;

import maven.blackjack2.Hand;
import maven.blackjack2.Deck;
import maven.blackjack2.standing_simulator.*;
import maven.blackjack2.hitting_simulator.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext ctx = new FileSystemXmlApplicationContext("file:/Users/brian/bj.xml");
    	
    	Hand player = new Hand();
		Hand dealer = new Hand();
		Deck deck = new Deck(6);

		for (int dealer_card=10; dealer_card > 0; --dealer_card) {			
			System.out.println(cardToString(dealer_card));
			deck.deal_card(dealer, dealer_card);
			for (int player_card_1 = 1; player_card_1 <= 10; player_card_1++) {
				deck.deal_card(player, player_card_1);
				for (int player_card_2 = 1; player_card_2 <= player_card_1; player_card_2++) {
					deck.deal_card(player, player_card_2);

					Double pct = 0.0;

					HittingSimulatorService ss = (HittingSimulatorService) ctx.getBean("HittingSimulator", player, dealer, deck);
					pct = ss.expected_return();

//					int pct_int = (int) Math.round(pct * 10000);
					int pct_int = (int) (pct * 1000000);
					System.out.print(pct_int);
					System.out.print(" ");
					deck.undeal_card(player, player_card_2);
				}
				deck.undeal_card(player, player_card_1);
				System.out.println();
			}
			deck.undeal_card(dealer, dealer_card);
			System.out.println();
		}
		System.out.println();
    }
    
    static String cardToString(int card) {
    	if ( card == 1 ) {
    		return "A";
    	}
    	return String.valueOf(card);
    }
}
    	

