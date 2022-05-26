package mobile.android.prototype.ui.home;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HomeViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {

    }

    @Test
    public void getTextReturnsDefault() {
        // arrange
        HomeViewModel vm = new HomeViewModel();

        String expectedString = "Example of a BLE scanner prototype.";

        // act
        String actual = vm.getText().getValue();

        // assert
        Assert.assertEquals(expectedString, actual);
    }
}