package com.bookservice.ui.activity.home;

import com.bookservice.data.model.category.CategoryResponse;
import com.bookservice.data.model.offers.GetOffersResponse;

public interface IHomeView {

    public void onSuccessOffers(GetOffersResponse result);
    public void onSuccessCategory(CategoryResponse result);
    public void onError(String message);


}
