package com.khadamat.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class Validation
{
    Context context;

    public Validation(Context context)
    {
        this.context = context;
    }

    public boolean validationOfDay(String day)
    {
        if (TextUtils.isEmpty(day))
        {
            Toast.makeText(context, "من فضلك أدخل اليوم الذي تريد الحجز فيه", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean validationOfTime(String time)
    {
        if (TextUtils.isEmpty(time))
        {
            Toast.makeText(context, "من فضلك أدخل الساعة التي تريد الحجز فيها", Toast.LENGTH_LONG).show();
            return false;
        }

        else
        {
            return true;
        }
    }
}
