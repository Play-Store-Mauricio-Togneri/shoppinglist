package com.mauriciotogneri.shoppinglist.views;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;

import com.mauriciotogneri.androidutils.Keyboard;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.adapters.SearchImageAdapter;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.SearchImageViewObserver;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.ViewContainer;

import java.util.List;

public class SearchImageView extends BaseView<SearchImageViewObserver, ViewContainer>
{
    private SearchImageAdapter adapter;

    public SearchImageView(SearchImageViewObserver observer)
    {
        super(R.layout.screen_search_image, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        ui.buttonClose.setOnClickListener(v -> observer.onBack());
        ui.buttonSearch.setOnClickListener(v -> search());

        ui.input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                search();

                return true;
            }
            return false;
        });
        ui.input.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                ui.buttonSearch.setClickable(!TextUtils.isEmpty(ui.input.getText()));
            }
        });

        adapter = new SearchImageAdapter(context());
        ui.grid.setAdapter(adapter);
        ui.grid.setOnItemClickListener((parent, view, position, id) -> observer.onImageSelected((String) parent.getItemAtPosition(position)));
    }

    public void query(String initialQuery)
    {
        ui.input.setText(initialQuery);
        search();
    }

    public void loadImages(List<String> images)
    {
        adapter.loadImages(images);
        presentationMode();
    }

    public void loadingMode()
    {
        ui.grid.setVisibility(View.GONE);
        ui.loading.setVisibility(View.VISIBLE);
    }

    private void presentationMode()
    {
        ui.grid.setVisibility(View.VISIBLE);
        ui.loading.setVisibility(View.GONE);
    }

    private void search()
    {
        String query = ui.input.getText().toString();

        if (!TextUtils.isEmpty(query))
        {
            observer.onSearch(query);

            Keyboard keyboard = new Keyboard(context());
            keyboard.hide(ui.input);
        }
    }

    public interface SearchImageViewObserver
    {
        void onBack();

        void onSearch(String query);

        void onImageSelected(String url);
    }

    public static class ViewContainer
    {
        @BindView(R.id.toolbar_close)
        public View buttonClose;

        @BindView(R.id.toolbar_input)
        public EditText input;

        @BindView(R.id.toolbar_search)
        public View buttonSearch;

        @BindView(R.id.grid)
        public GridView grid;

        @BindView(R.id.loading)
        public View loading;
    }
}