package com.TradeX.service;

import com.TradeX.modal.Coin;
import java.util.List;

public interface CoinService  {

    List<Coin> getCoinList (int page);


    String getMarketChart(String coinId,int days);

    String getCoinDetails(String coinId);

    String findById(String coinId);

    String searchCoin(String keyword);

    String getTop50CoinByMarketCapRank();

    String getTradingCoins();

}
