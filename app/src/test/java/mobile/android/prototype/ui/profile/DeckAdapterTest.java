package mobile.android.prototype.ui.profile;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DeckAdapterTest {

    @Test
    public void deckAdapterAddsItems() {
        // arrange
        Context c = Mockito.mock(Context.class);
        List<CardItemModel> list = Mockito.mock(ArrayList.class);
        DeckAdapter deckAdapter = new DeckAdapter(list, c);

        List<CardItemModel> items = new ArrayList<>();
        items.add(new CardItemModel("test Name", new ArrayList<>(), ""));

        // act
        deckAdapter.addItems(items);

        // assert
        Mockito.verify(list, Mockito.atLeastOnce()).addAll(items);
    }

    @Test
    public void deckAdapterContainsListAfterInstantiation() {
        // arrange
        Context c = Mockito.mock(Context.class);
        List<CardItemModel> items = new ArrayList<>();
        items.add(new CardItemModel("test Name", new ArrayList<>(), ""));
        DeckAdapter deckAdapter = new DeckAdapter(new ArrayList<>(), c);

        // act
        deckAdapter.addItems(items);

        // assert
        Assert.assertEquals(1, deckAdapter.getCount());
    }

    @Test
    public void deckAdapterIncrementListAfterAdd() {
        // arrange
        Context c = Mockito.mock(Context.class);
        List<CardItemModel> items = new ArrayList<>();
        items.add(new CardItemModel("test Name", new ArrayList<>(), ""));
        DeckAdapter deckAdapter = new DeckAdapter(items, c);

        // act
        deckAdapter.addItems(items);

        // assert
        Assert.assertEquals(2, deckAdapter.getCount());
    }

    @Test
    public void deckAdapterReturnsZeroCountOnNullList() {
        // arrange
        Context c = Mockito.mock(Context.class);

        // act
        DeckAdapter deckAdapter = new DeckAdapter(null, c);

        // assert
        Assert.assertEquals(0, deckAdapter.getCount());
    }

}