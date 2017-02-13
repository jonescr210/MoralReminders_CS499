package cs499_uab_capstone_project.moralreminders.logic;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import java.util.Random;

import cs499_uab_capstone_project.moralreminders.R;

/**
 * Class to generate random ID's
 * required to retrieve Random messgae from SQLiTE DB
 */
public class RandomGenerator {

    /**
     * Method to generate random message ID
     * @param range Pool of numbers to be randomized.
     */
   public static int getRandomMessageId(int range) {

       //TODO - add implementation here.
       Random random = new Random();
       return random.nextInt(range);

   }

    /**
     * Generate random color from list of colors
     */
    public static int getRandomColorId(Context context) {
        int color = -1;

        if (context == null) return color;


        try {
             int[] colorList = context.getResources().getIntArray(R.array.bg_color_list);

            Random random = new Random();
            color = colorList[random.nextInt(colorList.length)];


        } catch (Resources.NotFoundException e){

            Log.e("RandomGenerator", e.getMessage());

        }

        return color;
    }

    public static Uri getRandomSound(Context context) {
        Uri result = null;


        return result;
    }

}
