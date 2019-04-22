
package com.bookservice.data.model.offers;

import java.util.List;

public class GetOffersResponse {

    private String url;
    private List<Offer> offers = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
