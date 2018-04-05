package com.mauriciotogneri.shoppinglist.views;

import com.mauriciotogneri.shoppinglist.R;
import com.mauriciotogneri.shoppinglist.base.BaseView;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.SearchImageViewObserver;
import com.mauriciotogneri.shoppinglist.views.SearchImageView.ViewContainer;

public class SearchImageView extends BaseView<SearchImageViewObserver, ViewContainer>
{
    public SearchImageView(SearchImageViewObserver observer)
    {
        super(R.layout.screen_search_image, observer, new ViewContainer());
    }

    @Override
    protected void initialize()
    {
        // TODO: title
        toolbarTitle(R.string.toolbar_title_add_product);
        enableBack(v -> observer.onBack());


    }

    public interface SearchImageViewObserver
    {
        void onBack();

        void onImageSelected(String url);
    }

    public static class ViewContainer
    {
        //@BindView(R.id.product_list)
        //public ListView list;
    }
}