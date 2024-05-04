package com.mybuy.model;

import com.mybuy.dao.AuctionDAO;
import com.mybuy.dao.IAuctionDAO;

import java.util.ArrayList;
import java.util.List;

public class AuctionModel {
    private IAuctionDAO auctionDAO;
    private ItemModel itemModel;

    public AuctionModel() {
        this.auctionDAO = new AuctionDAO();
        this.itemModel = new ItemModel();
    }

    public List<Auction> getAuctionsByUsername(String username) {
        List<Auction> auctions = auctionDAO.getAuctionsByUsername(username);
        for(Auction auction : auctions) {
            auction.setItem(itemModel.getItem(auction.getItemId()));
        }
        return auctions;
    }

    public int addAuction(Auction auction) {
        return auctionDAO.addAuction(auction);
    }

    public Auction getAuctionById(int auctionId) {
        Auction auction = auctionDAO.getAuctionById(auctionId);
        auction.setItem(itemModel.getItem(auction.getItemId()));
        return auction;
    }

    public List<Auction> getSimilarAuctions(int itemId) {
        List<Auction> auctions = auctionDAO.getSimilarAuctionsByItemId(itemId);
        for(Auction auction : auctions) {
            auction.setItem(itemModel.getItem(auction.getItemId()));
        }
        return auctions;
    }
}
