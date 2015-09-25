package com.mauriciotogneri.shoppinglist.views.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.base.BaseDialog;
import com.mauriciotogneri.common.widgets.CustomEditText;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.model.Category;
import com.mauriciotogneri.shoppinglist.views.managecategories.ManageCategoriesViewObserver;

public class DialogEditCategory extends BaseDialog
{
    private String selectedColor = "";

    public DialogEditCategory(Context context, int title)
    {
        super(context, context.getString(title));
    }

    public void initialize(final Category category, final ManageCategoriesViewObserver observer)
    {
        selectedColor = (category == null) ? Category.COLOR_1 : category.getColor();

        final CustomEditText categoryName = (CustomEditText) view.findViewById(R.id.name);

        if (category != null)
        {
            categoryName.setTextValue(category.getName());
        }

        setColorButtonCallback(R.id.color_1, Category.COLOR_1, selectedColor);
        setColorButtonCallback(R.id.color_2, Category.COLOR_2, selectedColor);
        setColorButtonCallback(R.id.color_3, Category.COLOR_3, selectedColor);
        setColorButtonCallback(R.id.color_4, Category.COLOR_4, selectedColor);
        setColorButtonCallback(R.id.color_5, Category.COLOR_5, selectedColor);
        setColorButtonCallback(R.id.color_6, Category.COLOR_6, selectedColor);
        setColorButtonCallback(R.id.color_7, Category.COLOR_7, selectedColor);
        setColorButtonCallback(R.id.color_8, Category.COLOR_8, selectedColor);

        setPositiveButton(R.string.button_accept, null);
        setPositiveButtonAction(new OnAccept()
        {
            @Override
            public void onAccept(BaseDialog dialog)
            {
                boolean closeDialog = observer.onEditCategory(category, categoryName.getTextValue(), selectedColor);

                if (closeDialog)
                {
                    dialog.close();
                }
            }
        });

        setNegativeButton(R.string.button_cancel, null);
    }

    private void setColorButtonCallback(final int textViewId, String colorCode, String selectedColor)
    {
        TextView customTextView = (TextView) view.findViewById(textViewId);
        customTextView.setBackgroundColor(Color.parseColor("#" + colorCode));

        if (selectedColor.equals(colorCode))
        {
            selectColor(textViewId);
        }

        customTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectColor(textViewId);
            }
        });
    }

    private void selectColor(int colorId)
    {
        TextView color1 = (TextView) view.findViewById(R.id.color_1);
        color1.setText("");

        TextView color2 = (TextView) view.findViewById(R.id.color_2);
        color2.setText("");

        TextView color3 = (TextView) view.findViewById(R.id.color_3);
        color3.setText("");

        TextView color4 = (TextView) view.findViewById(R.id.color_4);
        color4.setText("");

        TextView color5 = (TextView) view.findViewById(R.id.color_5);
        color5.setText("");

        TextView color6 = (TextView) view.findViewById(R.id.color_6);
        color6.setText("");

        TextView color7 = (TextView) view.findViewById(R.id.color_7);
        color7.setText("");

        TextView color8 = (TextView) view.findViewById(R.id.color_8);
        color8.setText("");

        TextView color = (TextView) view.findViewById(colorId);
        color.setText(R.string.icon_update);

        ColorDrawable colorDrawable = (ColorDrawable) color.getBackground();
        selectedColor = String.format("%06X", 0xFFFFFF & colorDrawable.getColor());
    }

    @Override
    protected int getViewId()
    {
        return R.layout.dialog_create_category;
    }
}