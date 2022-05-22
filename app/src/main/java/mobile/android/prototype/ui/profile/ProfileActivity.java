package mobile.android.prototype.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;

import mobile.android.prototype.R;


public class ProfileActivity extends AppCompatActivity {
    private SwipeDeck cardStack;
    private ArrayList<CardItemModel> cardItemModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // on below line we are initializing our array list and swipe deck.
        cardItemModelArrayList = new ArrayList<>();
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        // on below line we are adding data to our array list.
        cardItemModelArrayList.add(new CardItemModel("Lamp1", null, "https://www.ikea.com/dk/da/images/products/ikea-ps-2014-loftlampe-hvid-kobberfarvet__0880877_pe613967_s5.jpg"));
        cardItemModelArrayList.add(new CardItemModel("Lamp2", null, "https://www.ikea.com/dk/da/images/products/tokabo-bordlampe-glas-gron__0879821_pe724779_s5.jpg"));
        cardItemModelArrayList.add(new CardItemModel("Lamp3", null, "https://www.ikea.com/dk/da/images/products/bunkeflo-loftlampe-hvid-birk__1008760_pe827315_s5.jpg"));
        cardItemModelArrayList.add(new CardItemModel("Lamp4", null, "https://www.ikea.com/dk/da/images/products/grindfallet-loftlampe-sort__1008889_pe827368_s5.jpg"));
        cardItemModelArrayList.add(new CardItemModel("Lamp5", null,"https://www.ikea.com/dk/da/images/products/arstid-bordlampe-messing-hvid__0880725_pe617347_s5.jpg"));

        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(cardItemModelArrayList, this);

        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);

        // on below line we are setting event callback to our card stack.
        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(ProfileActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(ProfileActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(ProfileActivity.this, "No more courses present", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });
    }
}
